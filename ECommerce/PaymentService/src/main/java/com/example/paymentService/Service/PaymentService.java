package com.example.paymentService.Service;

import com.example.OrderService.Entity.Order;
import com.example.OrderService.Payload.OrderDTO;
import com.example.paymentService.Dao.PaymentRepository;
import com.example.paymentService.Entity.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${item.service.url}")
    private String itemServiceUrl;

    public void processOrder(OrderDTO orderDTO) {

        // Check inventory for all items in the order
        boolean isInventoryAvailable = true;
        for (String itemId : orderDTO.getItemIds()) {
            // 1 item for now
            String inventoryCheckUrl = itemServiceUrl + "/api/v1/items/" + itemId + "/inventory?requestAmount=1";
            try {
                ResponseEntity<Boolean> response = restTemplate.getForEntity(inventoryCheckUrl, Boolean.class);
                if (response.getStatusCode() != HttpStatus.OK || !response.getBody()) {
                    isInventoryAvailable = false;
                    break;
                }
            } catch (RestClientException e) {
                logger.error("Error checking inventory for item {}: {}", itemId, e.getMessage());
                isInventoryAvailable = false;
                break;
            }
        }

        /*Optional<Payment> paymentOpt = paymentRepository.findByUserIdAndPaymentMethod(
                orderDTO.getUserId(), "credit_card");*/
        Payment paymentOpt = paymentRepository.findByUserId(
                orderDTO.getUserId());

        if (paymentOpt!=null && isInventoryAvailable) {
            Payment payment = paymentOpt;
            if (payment.getAmount().compareTo(orderDTO.getTotalAmount()) >= 0) {
                // Deduct the amount from the user's balance
                payment.setAmount(payment.getAmount().subtract(orderDTO.getTotalAmount()));
                paymentRepository.save(payment);

                // Update order status to SUCCESS
                updateOrderStatus(orderDTO.getOrderId(), "SUCCESS");


                //Update Inventory
                for (String itemId : orderDTO.getItemIds()) {
                    String updateInventoryUrl = itemServiceUrl + "/api/v1/items/" + itemId + "?addAmount=-1";
                    try {
                        restTemplate.put(updateInventoryUrl, null, Void.class);
                    } catch (RestClientException e) {
                        logger.error("Error updating inventory for item {}: {}", itemId, e.getMessage());
                    }
                }
            } else {
                // Update order status to FAILED
                updateOrderStatus(orderDTO.getOrderId(), "FAILED");
            }
        } else {
            // Update order status to FAILED if payment info is not found
            updateOrderStatus(orderDTO.getOrderId(), "FAILED");
        }
    }

    public Payment createPayment(Payment payment) {
        Payment p = new Payment();
        //order.setOrderId(UUID.randomUUID());
        p.setUserId(payment.getUserId());
        p.setPaymentMethod(payment.getPaymentMethod());
        p.setAmount(payment.getAmount());


        Payment savedPayment = paymentRepository.save(p);


        return savedPayment;
    }

    private void updateOrderStatus(String orderId, String status) {
        String url = orderServiceUrl + "/api/v1/orders/" + orderId + "/status?status=" + status;
        restTemplate.put(url, status);
    }

    public Payment getPaymentByUserId(String userId) {
        Payment payment = paymentRepository.findByUserId(userId);
        return payment;
    }

    public Payment addPaymentAmount(String userId, BigDecimal amount) {
        Payment payment = paymentRepository.findByUserId(userId);
        BigDecimal curr = payment.getAmount();
        curr = curr.add(amount);
        payment.setAmount(curr);

        Payment updatedPayment = paymentRepository.save(payment);

        return updatedPayment;
    }
    public List<Payment> getAllOrders() {
        return paymentRepository.findAll();
    }
}

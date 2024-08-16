package com.example.paymentService.Service;

import com.example.OrderService.Payload.OrderDTO;
import com.example.paymentService.Dao.PaymentRepository;
import com.example.paymentService.Entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public void processOrder(OrderDTO orderDTO) {
        Optional<Payment> paymentOpt = paymentRepository.findByUserIdAndPaymentMethod(
                orderDTO.getUserId(), "DEFAULT");

        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            if (payment.getAmount().compareTo(orderDTO.getTotalAmount()) >= 0) {
                // Deduct the amount from the user's balance
                payment.setAmount(payment.getAmount().subtract(orderDTO.getTotalAmount()));
                paymentRepository.save(payment);

                // Update order status to SUCCESS
                updateOrderStatus(orderDTO.getOrderId(), "SUCCESS");
            } else {
                // Update order status to FAILED
                updateOrderStatus(orderDTO.getOrderId(), "FAILED");
            }
        } else {
            // Update order status to FAILED if payment info is not found
            updateOrderStatus(orderDTO.getOrderId(), "FAILED");
        }
    }

    private void updateOrderStatus(String orderId, String status) {
        String url = orderServiceUrl + "/api/v1/orders/" + orderId + "/status?status=" + status;
        restTemplate.patchForObject(url, null, OrderDTO.class);
    }
}

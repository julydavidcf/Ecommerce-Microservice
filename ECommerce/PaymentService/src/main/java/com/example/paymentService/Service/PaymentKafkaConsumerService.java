package com.example.paymentService.Service;

import com.example.OrderService.Payload.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentKafkaConsumerService {

    private final Logger logger = LoggerFactory.getLogger(PaymentKafkaConsumerService.class);

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "orders", groupId = "payment-service-group")
    public void listen(String message) {
        logger.info("Received order message: {}", message);

        try {
            // Extract fields from JSON string
            String orderId = extractField(message, "orderId");
            String userId = extractField(message, "userId");
            List<String> itemIds = extractListField(message, "itemIds");
            BigDecimal totalAmount = new BigDecimal(extractField(message, "totalAmount"));
            String status = extractField(message, "status");
            // Convert to OrderDTO
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(orderId);
            orderDTO.setUserId(userId);
            orderDTO.setItemIds(itemIds);
            orderDTO.setTotalAmount(totalAmount);
            orderDTO.setStatus(status);

            // Process the order
            paymentService.processOrder(orderDTO);
        } catch (Exception e) {
            logger.error("Error processing order message: {}", e.getMessage());
        }
    }
    private String extractField(String message, String fieldName) {
        // Extract the value of a specific field from the JSON string
        String key = "\"" + fieldName + "\":\"";
        int startIndex = message.indexOf(key) + key.length();
        int endIndex = message.indexOf("\"", startIndex);
        return message.substring(startIndex, endIndex);
    }

    private List<String> extractListField(String message, String fieldName) {
        // Extract a list of values from a JSON string
        String key = "\"" + fieldName + "\":";
        int startIndex = message.indexOf(key) + key.length();
        int endIndex = message.indexOf("]", startIndex);
        String listStr = message.substring(startIndex, endIndex + 1);
        // Remove brackets and split by comma
        listStr = listStr.replace("[", "").replace("]", "").replace("\"", "");
        return Arrays.asList(listStr.split(","));
    }
}

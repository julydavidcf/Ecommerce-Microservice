package com.example.OrderService.Service;

import com.example.OrderService.Entity.Order;
import com.example.OrderService.Payload.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaProducerService {
    private static final String TOPIC = "orders";

    private final Logger logger = LoggerFactory.getLogger(OrderKafkaProducerService.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage( OrderDTO order) {

        String json = String.format("{\"orderId\":\"%s\",\"userId\":\"%s\",\"itemIds\":%s,\"totalAmount\":\"%s\",\"status\":\"%s\"}",
                order.getOrderId(),
                order.getUserId(),
                order.getItemIds().toString(),
                order.getTotalAmount().toString(),
                order.getStatus());
        logger.info("Sending message to Kafka: {}", json);
        kafkaTemplate.send(TOPIC, json);
    }
}

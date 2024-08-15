package com.example.OrderService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "orders";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String orderDetails) {
        this.kafkaTemplate.send(TOPIC, orderDetails);
    }
}

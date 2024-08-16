package com.example.paymentService.Service;

import com.example.OrderService.Payload.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaConsumerService {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "orders", groupId = "payment-service-group")
    public void consume(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            OrderDTO orderDTO = mapper.readValue(message, OrderDTO.class);
            paymentService.processOrder(orderDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
package com.example.OrderService.Controller;

import com.example.OrderService.Service.OrderKafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private OrderKafkaProducerService kafkaProducerService;

    @PostMapping("/publish")
    public String publishMessage(@RequestParam("key") String key, @RequestParam("message") String message) {
        kafkaProducerService.sendMessage( message);
        return "Message published successfully";
    }
}
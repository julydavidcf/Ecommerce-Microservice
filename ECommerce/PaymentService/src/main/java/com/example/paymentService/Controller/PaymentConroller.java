package com.example.paymentService.Controller;

import com.example.OrderService.Payload.OrderDTO;
import com.example.paymentService.Entity.Payment;
import com.example.paymentService.Service.PaymentService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentConroller {
    @Autowired
    private PaymentService paymentService;

    @CrossOrigin(origins = "http://localhost:8083")
    @PostMapping
    public ResponseEntity<Payment> createOrder(@RequestBody Payment payment) {
        Payment response = paymentService.createPayment(payment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Payment> getPaymentByID(@PathVariable String userId) {
        Payment response = paymentService.getPaymentByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> response = paymentService.getAllOrders();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<Payment> addPaymentAmount(@PathVariable String userId, @RequestParam BigDecimal addAmount) {
        Payment response = paymentService.addPaymentAmount(userId,addAmount);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

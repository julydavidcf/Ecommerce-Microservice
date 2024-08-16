package com.example.paymentService.Dao;

import com.example.paymentService.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByUserIdAndPaymentMethod(String userId, String paymentMethod);
}

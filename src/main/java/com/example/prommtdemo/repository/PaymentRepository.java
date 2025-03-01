package com.example.prommtdemo.repository;

import com.example.prommtdemo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

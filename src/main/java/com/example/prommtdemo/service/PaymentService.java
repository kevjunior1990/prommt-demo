package com.example.prommtdemo.service;

import com.example.prommtdemo.entity.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> getPayments();

    Payment getPayment(Long id);

    Payment createPayment(Payment payment);

    Payment updatePayment(Payment payment);

    void deletePayment(Long id);
}

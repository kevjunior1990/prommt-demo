package com.example.prommtdemo.controller;

import com.example.prommtdemo.entity.Payment;
import com.example.prommtdemo.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/payment/", produces = "application/json")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {

        return ResponseEntity.ok(paymentService.getPayments());
    }

    // Endpoint to get a payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {

        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    // Endpoint to create a new payment
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(payment));
    }

    // Endpoint to update an existing payment
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {

        log.info("Updating payment status with ID: {}", id);
        payment.setId(id);
        return ResponseEntity.ok(paymentService.updatePayment(payment));
    }

    // Endpoint to delete a payment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {

        log.info("Deleting payment with ID: {}", id);
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.prommtdemo.service;

import com.example.prommtdemo.entity.Payment;
import com.example.prommtdemo.exception.PaymentNotFoundException;
import com.example.prommtdemo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with ID " + id + " not found"));
    }


    @Override
    public Payment createPayment(Payment payment) {

        try {
            return paymentRepository.save(payment);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Database constraint violated while saving payment", e);
        }
    }

    @Override
    public Payment updatePayment(Payment payment) {

        if (!paymentRepository.existsById(payment.getId())) {

            throw new PaymentNotFoundException("Payment with ID " + payment.getId() + " not found");
        }

        return paymentRepository.save(payment);
    }


    @Override
    public void deletePayment(Long id) {

        if (!paymentRepository.existsById(id)) {

            throw new PaymentNotFoundException("Payment with ID " + id + " not found");
        }

        paymentRepository.deleteById(id);
    }
}

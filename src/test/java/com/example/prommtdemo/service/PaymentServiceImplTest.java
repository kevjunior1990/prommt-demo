package com.example.prommtdemo.service;

import com.example.prommtdemo.entity.Payment;
import com.example.prommtdemo.exception.PaymentNotFoundException;
import com.example.prommtdemo.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
        payment.setId(1L);
        payment.setEmail("payer@example.com");
        payment.setAmount(new BigDecimal("100.00"));
        payment.setCurrency("USD");
    }

    @Test
    void testGetPayments() {
        // Arrange: Mock the repository to return a list of payments
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment));

        // Act: Call the service method
        List<Payment> payments = paymentService.getPayments();

        // Assert: Verify that the service method returns the expected result
        assertNotNull(payments);
        assertEquals(1, payments.size());
        assertEquals("payer@example.com", payments.get(0).getEmail());
    }

    @Test
    void testGetPayment() {
        // Arrange: Mock the repository to return a payment when findById is called
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        // Act: Call the service method
        Payment foundPayment = paymentService.getPayment(1L);

        // Assert: Verify that the service method returns the expected result
        assertNotNull(foundPayment);
        assertEquals("payer@example.com", foundPayment.getEmail());
    }

    @Test
    void testGetPaymentThrowsExceptionWhenNotFound() {
        // Arrange: Mock the repository to return empty for findById
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verify that a RuntimeException is thrown when payment is not found
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> paymentService.getPayment(1L));
        assertEquals("Payment with ID 1 not found", thrown.getMessage());
    }

    @Test
    void testCreatePayment() {
        // Arrange: Mock the repository to save the payment and return the same payment
        when(paymentRepository.save(payment)).thenReturn(payment);

        // Act: Call the service method
        Payment createdPayment = paymentService.createPayment(payment);

        // Assert: Verify that the service method returns the expected result
        assertNotNull(createdPayment);
        assertEquals("payer@example.com", createdPayment.getEmail());
    }

    @Test
    void testCreatePaymentThrowsExceptionOnError() {
        // Arrange: Mock the repository to throw an exception
        when(paymentRepository.save(payment)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert: Verify that a RuntimeException is thrown when saving the payment fails
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> paymentService.createPayment(payment));
        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    void testUpdatePayment() {
        // Arrange: Mock the repository to save the payment and return the updated payment
        when(paymentRepository.existsById(1L)).thenReturn(true);
        when(paymentRepository.save(payment)).thenReturn(payment);

        // Act: Call the service method
        Payment updatedPayment = paymentService.updatePayment(payment);

        // Assert: Verify that the service method returns the expected result
        assertNotNull(updatedPayment);
        assertEquals("payer@example.com", updatedPayment.getEmail());
    }

    @Test
    void testUpdatePaymentThrowsExceptionWhenNotFound() {
        // Arrange: Mock the repository to return false for existsById
        when(paymentRepository.existsById(1L)).thenReturn(false);

        // Act & Assert: Verify that an IllegalArgumentException is thrown when payment is not found
        PaymentNotFoundException thrown = assertThrows(PaymentNotFoundException.class, () -> paymentService.updatePayment(payment));
        assertEquals("Payment with ID 1 not found", thrown.getMessage());
    }

    @Test
    void testDeletePayment() {
        // Arrange: Mock the repository to return true for existsById
        when(paymentRepository.existsById(1L)).thenReturn(true);

        // Act: Call the service method
        paymentService.deletePayment(1L);

        // Assert: Verify that deleteById was called once
        verify(paymentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePaymentThrowsExceptionWhenNotFound() {
        // Arrange: Mock the repository to return false for existsById
        when(paymentRepository.existsById(1L)).thenReturn(false);

        // Act & Assert: Verify that an IllegalArgumentException is thrown when payment is not found
        PaymentNotFoundException thrown = assertThrows(PaymentNotFoundException.class, () -> paymentService.deletePayment(1L));
        assertEquals("Payment with ID 1 not found", thrown.getMessage());
    }
}

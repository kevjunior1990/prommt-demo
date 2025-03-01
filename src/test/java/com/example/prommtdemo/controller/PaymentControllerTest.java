package com.example.prommtdemo.controller;

import com.example.prommtdemo.entity.Payment;
import com.example.prommtdemo.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        payment = Payment.builder()
                .id(1L)
                .email("payer@example.com")
                .currency("USD")
                .amount(BigDecimal.valueOf(100))
                .createdDate(Timestamp.valueOf(LocalDateTime.now().minusDays(10L)))
                .paidDate(Timestamp.valueOf(LocalDateTime.now().minusDays(5L)))
                .build();
    }

    @Test
    void getPayments() {
        // Mock the service method to return a list of payments
        when(paymentService.getPayments()).thenReturn(Arrays.asList(payment));

        // Perform the controller method
        ResponseEntity<List<Payment>> response = paymentController.getPayments();

        // Validate the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("payer@example.com", response.getBody().get(0).getEmail());
    }

    @Test
    void getPaymentById_Success() {
        // Mock the service method to return a payment by ID
        when(paymentService.getPayment(1L)).thenReturn(payment);

        // Perform the controller method
        ResponseEntity<Payment> response = paymentController.getPaymentById(1L);

        // Validate the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("payer@example.com", response.getBody().getEmail());
    }

    @Test
    void createPayment() {
        // Mock the service method to return the created payment
        when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

        // Perform the controller method
        ResponseEntity<Payment> response = paymentController.createPayment(payment);

        // Validate the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("payer@example.com", response.getBody().getEmail());
    }

    @Test
    void updatePayment_Success() {
        // Mock the service method to return the updated payment
        when(paymentService.updatePayment(any(Payment.class))).thenReturn(payment);

        // Perform the controller method
        ResponseEntity<Payment> response = paymentController.updatePayment(1L, payment);

        // Validate the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("payer@example.com", response.getBody().getEmail());
    }

    @Test
    void deletePayment_Success() {
        // Perform the controller method
        doNothing().when(paymentService).deletePayment(1L);

        ResponseEntity<Void> response = paymentController.deletePayment(1L);

        // Validate the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(paymentService, times(1)).deletePayment(1L);
    }
}

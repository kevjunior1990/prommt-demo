package com.example.prommtdemo.repository;

import com.example.prommtdemo.entity.Payment;
import com.example.prommtdemo.enums.PaymentStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = Payment.builder()
                .email("test@example.com")
                .status(PaymentStatus.PENDING)
                .currency("USD")
                .amount(new BigDecimal("100.00"))
                .createdDate(Timestamp.valueOf(LocalDateTime.now().minusDays(10L)))
                .build();

        paymentRepository.save(testPayment);
    }

    @AfterEach
    void tearDown() {
        paymentRepository.deleteAll();
    }

    @Test
    void testSavePayment() {
        Payment payment = Payment.builder()
                .email("user@example.com")
                .status(PaymentStatus.COMPLETED)
                .currency("EUR")
                .amount(new BigDecimal("250.00"))
                .createdDate(Timestamp.valueOf(LocalDateTime.now().minusDays(10L)))
                .paidDate(Timestamp.valueOf(LocalDateTime.now().minusDays(5L)))
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        assertThat(savedPayment).isNotNull();
        assertThat(savedPayment.getId()).isNotNull();
        assertThat(savedPayment.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    void testFindById() {
        Optional<Payment> foundPayment = paymentRepository.findById(testPayment.getId());

        assertThat(foundPayment).isPresent();
        assertThat(foundPayment.get().getEmail()).isEqualTo("test@example.com");
    }

    /*@Test
    void testFindByEmail() {
        List<Payment> payments = paymentRepository.findByEmail("test@example.com");

        assertThat(payments).isNotEmpty();
        assertThat(payments.get(0).getStatus()).isEqualTo(PaymentStatus.PENDING);
    }*/

    @Test
    void testUpdatePaymentStatus() {
        testPayment.setStatus(PaymentStatus.COMPLETED);
        Payment updatedPayment = paymentRepository.save(testPayment);

        assertThat(updatedPayment.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
    }

    @Test
    void testDeletePayment() {
        paymentRepository.delete(testPayment);

        Optional<Payment> deletedPayment = paymentRepository.findById(testPayment.getId());

        assertThat(deletedPayment).isEmpty();
    }
}


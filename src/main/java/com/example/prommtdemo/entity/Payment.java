package com.example.prommtdemo.entity;

import com.example.prommtdemo.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PROMMT_PAYMENT")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Payment extends DateEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "PROMMT_PAYMENT_SEQ", sequenceName = "PROMMT_PAYMENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "PROMMT_PAYMENT_SEQ")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "PAYER_EMAIL", nullable = false)
    private String email;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
}

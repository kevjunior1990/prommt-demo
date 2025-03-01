package com.example.prommtdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class DateEntity {

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @CreatedDate
    private Timestamp createdDate;

    @Column(name = "PAID_DATE")
    @LastModifiedDate
    private Timestamp paidDate;
}

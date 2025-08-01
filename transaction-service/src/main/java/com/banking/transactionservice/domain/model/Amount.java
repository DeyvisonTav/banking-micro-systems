package com.banking.transactionservice.domain.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public record Amount(BigDecimal value) {
    
    public Amount {
        Objects.requireNonNull(value, "Amount value cannot be null");
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (value.scale() > 2) {
            throw new IllegalArgumentException("Amount cannot have more than 2 decimal places");
        }
    }
    
    public static Amount of(BigDecimal value) {
        return new Amount(value);
    }
    
    public static Amount of(double value) {
        return new Amount(BigDecimal.valueOf(value));
    }
    
    public static Amount of(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return new Amount(BigDecimal.valueOf(value));
    }
    
    public Integer toInteger() {
        return value.intValue();
    }
} 
package com.banking.accountservice.domain.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public record Balance(BigDecimal amount) {
    
    public Balance {
        Objects.requireNonNull(amount, "Balance amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        if (amount.scale() > 2) {
            throw new IllegalArgumentException("Balance cannot have more than 2 decimal places");
        }
    }
    
    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }
    
    public static Balance of(BigDecimal amount) {
        return new Balance(amount);
    }
    
    public static Balance of(double amount) {
        return new Balance(BigDecimal.valueOf(amount));
    }
    
    public static Balance of(Integer amount) {
        return amount != null ? new Balance(BigDecimal.valueOf(amount)) : zero();
    }
    
    public Balance add(Balance other) {
        return new Balance(this.amount.add(other.amount));
    }
    
    public Balance subtract(Balance other) {
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        return new Balance(result);
    }
    
    public boolean isGreaterThan(Balance other) {
        return this.amount.compareTo(other.amount) > 0;
    }
    
    public boolean isGreaterThanOrEqual(Balance other) {
        return this.amount.compareTo(other.amount) >= 0;
    }
    
    public Integer toInteger() {
        return amount.intValue();
    }
} 
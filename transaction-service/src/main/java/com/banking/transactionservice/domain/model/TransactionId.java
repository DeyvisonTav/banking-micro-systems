package com.banking.transactionservice.domain.model;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public record TransactionId(UUID value) {
    
    public TransactionId {
        Objects.requireNonNull(value, "TransactionId cannot be null");
    }
    
    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }
    
    public static TransactionId of(UUID value) {
        return new TransactionId(value);
    }
    
    public static TransactionId of(String value) {
        return new TransactionId(UUID.fromString(value));
    }
} 
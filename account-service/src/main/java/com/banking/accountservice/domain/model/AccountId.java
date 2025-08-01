package com.banking.accountservice.domain.model;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public record AccountId(UUID value) {
    
    public AccountId {
        Objects.requireNonNull(value, "AccountId cannot be null");
    }
    
    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }
    
    public static AccountId of(UUID value) {
        return new AccountId(value);
    }
    
    public static AccountId of(String value) {
        return new AccountId(UUID.fromString(value));
    }
} 
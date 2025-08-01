package com.banking.accountservice.domain.exception;

public class AccountAlreadyExistsException extends DomainException {
    
    public AccountAlreadyExistsException(String field, String value) {
        super("Account already exists with " + field + ": " + value);
    }
} 
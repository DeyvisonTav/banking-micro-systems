package com.banking.accountservice.domain.exception;

import com.banking.accountservice.domain.model.AccountId;

public class AccountNotFoundException extends DomainException {
    
    public AccountNotFoundException(AccountId accountId) {
        super("Account not found with id: " + accountId.value());
    }
    
    public AccountNotFoundException(String documentNumber) {
        super("Account not found with document number: " + documentNumber);
    }
    
    public AccountNotFoundException(String message) {
        super(message);
    }
} 
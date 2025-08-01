package com.banking.transactionservice.domain.exception;

import com.banking.transactionservice.domain.model.TransactionId;

public class TransactionNotFoundException extends DomainException {
    
    public TransactionNotFoundException(TransactionId transactionId) {
        super("Transaction not found with id: " + transactionId.value());
    }
    
    public TransactionNotFoundException(String message) {
        super(message);
    }
} 
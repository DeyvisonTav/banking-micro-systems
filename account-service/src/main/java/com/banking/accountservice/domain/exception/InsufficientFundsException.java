package com.banking.accountservice.domain.exception;

import com.banking.accountservice.domain.model.Balance;

public class InsufficientFundsException extends DomainException {
    
    public InsufficientFundsException(Balance currentBalance, Balance requiredAmount) {
        super("Insufficient funds. Current balance: " + currentBalance.amount() + 
              ", Required: " + requiredAmount.amount());
    }
} 
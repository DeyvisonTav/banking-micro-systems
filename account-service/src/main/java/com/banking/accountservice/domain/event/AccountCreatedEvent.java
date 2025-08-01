package com.banking.accountservice.domain.event;

import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.AccountId;
import com.banking.accountservice.domain.model.Balance;
import com.banking.accountservice.domain.model.DocumentNumber;
import com.banking.accountservice.domain.model.Email;

import java.time.LocalDateTime;

public record AccountCreatedEvent(
        AccountId accountId,
        String name,
        Email email,
        Balance balance,
        DocumentNumber documentNumber,
        LocalDateTime occurredOn
) implements DomainEvent {
    
    public static AccountCreatedEvent from(Account account) {
        return new AccountCreatedEvent(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getBalance(),
                account.getDocumentNumber(),
                LocalDateTime.now()
        );
    }
} 
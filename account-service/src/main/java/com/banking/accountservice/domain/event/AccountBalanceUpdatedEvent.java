package com.banking.accountservice.domain.event;

import com.banking.accountservice.domain.model.AccountId;
import com.banking.accountservice.domain.model.Balance;

import java.time.LocalDateTime;

public record AccountBalanceUpdatedEvent(
        AccountId accountId,
        Balance previousBalance,
        Balance newBalance,
        LocalDateTime occurredOn
) implements DomainEvent {
} 
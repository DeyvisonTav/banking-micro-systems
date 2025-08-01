package com.banking.transactionservice.domain.event;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.model.TransactionId;

import java.time.LocalDateTime;

public record TransactionStatusChangedEvent(
        TransactionId transactionId,
        TransactionStatus previousStatus,
        TransactionStatus newStatus,
        LocalDateTime occurredOn
) implements DomainEvent {
} 
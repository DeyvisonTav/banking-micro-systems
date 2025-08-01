package com.banking.transactionservice.domain.event;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.model.Amount;
import com.banking.transactionservice.domain.model.DocumentNumber;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionId;

import java.time.LocalDateTime;

public record TransactionCreatedEvent(
        TransactionId transactionId,
        DocumentNumber senderDocumentNumber,
        DocumentNumber receiverDocumentNumber,
        Amount amount,
        TransactionStatus status,
        LocalDateTime occurredOn
) implements DomainEvent {
    
    public static TransactionCreatedEvent from(Transaction transaction) {
        return new TransactionCreatedEvent(
                transaction.getId(),
                transaction.getSenderDocumentNumber(),
                transaction.getReceiverDocumentNumber(),
                transaction.getAmount(),
                transaction.getStatus(),
                LocalDateTime.now()
        );
    }
} 
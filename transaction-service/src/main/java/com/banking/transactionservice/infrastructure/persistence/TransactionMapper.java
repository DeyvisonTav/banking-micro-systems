package com.banking.transactionservice.infrastructure.persistence;

import com.banking.transactionservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    
    public TransactionEntity toEntity(Transaction transaction) {
        return new TransactionEntity(
                transaction.getId().value(),
                transaction.getSenderDocumentNumber().value(),
                transaction.getReceiverDocumentNumber().value(),
                transaction.getAmount().value(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
    
    public Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                TransactionId.of(entity.getId()),
                DocumentNumber.of(entity.getSenderDocumentNumber()),
                DocumentNumber.of(entity.getReceiverDocumentNumber()),
                Amount.of(entity.getAmount()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
} 
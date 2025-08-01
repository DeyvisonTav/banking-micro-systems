package com.banking.transactionservice.infrastructure.messaging;

import com.banking.transactionservice.domain.event.TransactionCreatedEvent;
import com.banking.transactionservice.domain.event.TransactionStatusChangedEvent;
import com.banking.transactionservice.infrastructure.messaging.dto.TransactionCreatedEventDTO;
import com.banking.transactionservice.infrastructure.messaging.dto.TransactionStatusChangedEventDTO;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    
    public TransactionCreatedEventDTO toDTO(TransactionCreatedEvent event) {
        return new TransactionCreatedEventDTO(
                event.transactionId().value(),
                event.senderDocumentNumber().value(),
                event.receiverDocumentNumber().value(),
                event.amount().toInteger(),
                event.status().name(),
                event.occurredOn()
        );
    }
    
    public TransactionStatusChangedEventDTO toDTO(TransactionStatusChangedEvent event) {
        return new TransactionStatusChangedEventDTO(
                event.transactionId().value(),
                event.previousStatus().name(),
                event.newStatus().name(),
                event.occurredOn()
        );
    }
} 
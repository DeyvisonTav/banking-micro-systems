package com.banking.transactionservice.infrastructure.messaging;

import com.banking.transactionservice.application.port.TransactionEventPublisher;
import com.banking.transactionservice.domain.event.DomainEvent;
import com.banking.transactionservice.domain.event.TransactionCreatedEvent;
import com.banking.transactionservice.domain.event.TransactionStatusChangedEvent;
import com.banking.transactionservice.infrastructure.messaging.dto.TransactionCreatedEventDTO;
import com.banking.transactionservice.infrastructure.messaging.dto.TransactionStatusChangedEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements TransactionEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    private final EventMapper eventMapper;
    
    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate, EventMapper eventMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventMapper = eventMapper;
    }
    
    @Override
    public void publish(DomainEvent event) {
        switch (event) {
            case TransactionCreatedEvent transactionCreated -> publishTransactionCreated(transactionCreated);
            case TransactionStatusChangedEvent statusChanged -> publishTransactionStatusChanged(statusChanged);
            default -> throw new IllegalArgumentException("Unsupported event type: " + event.getClass());
        }
    }
    
    private void publishTransactionCreated(TransactionCreatedEvent event) {
        TransactionCreatedEventDTO dto = eventMapper.toDTO(event);
        rabbitTemplate.convertAndSend("transaction.exchange", "transaction.created", dto);
    }
    
    private void publishTransactionStatusChanged(TransactionStatusChangedEvent event) {
        TransactionStatusChangedEventDTO dto = eventMapper.toDTO(event);
        rabbitTemplate.convertAndSend("transaction.exchange", "transaction.status.changed", dto);
    }
} 
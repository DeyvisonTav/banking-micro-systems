package com.banking.accountservice.infrastructure.messaging;

import com.banking.accountservice.application.port.AccountEventPublisher;
import com.banking.accountservice.domain.event.AccountCreatedEvent;
import com.banking.accountservice.domain.event.DomainEvent;
import com.banking.accountservice.infrastructure.messaging.dto.AccountCreatedEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements AccountEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    private final EventMapper eventMapper;
    
    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate, EventMapper eventMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventMapper = eventMapper;
    }
    
    @Override
    public void publish(DomainEvent event) {
        switch (event) {
            case AccountCreatedEvent accountCreated -> publishAccountCreated(accountCreated);
            default -> throw new IllegalArgumentException("Unsupported event type: " + event.getClass());
        }
    }
    
    private void publishAccountCreated(AccountCreatedEvent event) {
        AccountCreatedEventDTO dto = eventMapper.toDTO(event);
        rabbitTemplate.convertAndSend("account.exchange", "account.created", dto);
    }
} 
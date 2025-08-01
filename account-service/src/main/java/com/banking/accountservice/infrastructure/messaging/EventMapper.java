package com.banking.accountservice.infrastructure.messaging;

import com.banking.accountservice.domain.event.AccountCreatedEvent;
import com.banking.accountservice.infrastructure.messaging.dto.AccountCreatedEventDTO;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    
    public AccountCreatedEventDTO toDTO(AccountCreatedEvent event) {
        return new AccountCreatedEventDTO(
                event.accountId().value(),
                event.name(),
                event.email().value(),
                event.balance().toInteger(),
                event.documentNumber().type(),
                event.documentNumber().value(),
                event.occurredOn()
        );
    }
} 
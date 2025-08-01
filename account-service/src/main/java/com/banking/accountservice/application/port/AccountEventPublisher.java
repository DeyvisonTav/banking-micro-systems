package com.banking.accountservice.application.port;

import com.banking.accountservice.domain.event.DomainEvent;

public interface AccountEventPublisher {
    void publish(DomainEvent event);
} 
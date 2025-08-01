package com.banking.transactionservice.application.port;

import com.banking.transactionservice.domain.event.DomainEvent;

public interface TransactionEventPublisher {
    void publish(DomainEvent event);
} 
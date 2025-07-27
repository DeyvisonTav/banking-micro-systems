package com.banking.accountservice.messaging;
import com.banking.accountservice.messaging.event.AccountCreatedEvent;

public interface AccountEventPublisher {
    void publishAccountCreated(AccountCreatedEvent event);
}

package com.banking.accountservice.messaging.event;

import java.util.UUID;

public record AccountCreatedEvent(
        UUID accountId,
        String name,
        String email,
        Integer balance
) {}

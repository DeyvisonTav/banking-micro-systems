package com.banking.accountservice.messaging.event;

import com.banking.accountservice.domain.Enum.Document;

import java.util.UUID;

public record AccountCreatedEvent(
        UUID accountId,
        String name,
        String email,
        Integer balance,
        Document documentType,
        String documentNumber
) {}

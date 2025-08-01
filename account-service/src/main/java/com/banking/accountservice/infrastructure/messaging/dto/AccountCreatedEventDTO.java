package com.banking.accountservice.infrastructure.messaging.dto;

import com.banking.accountservice.domain.Enum.Document;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountCreatedEventDTO(
        UUID accountId,
        String name,
        String email,
        Integer balance,
        Document documentType,
        String documentNumber,
        LocalDateTime occurredOn
) {} 
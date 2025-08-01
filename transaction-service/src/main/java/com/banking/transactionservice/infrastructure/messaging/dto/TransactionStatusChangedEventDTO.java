package com.banking.transactionservice.infrastructure.messaging.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionStatusChangedEventDTO(
        UUID transactionId,
        String previousStatus,
        String newStatus,
        LocalDateTime occurredOn
) {} 
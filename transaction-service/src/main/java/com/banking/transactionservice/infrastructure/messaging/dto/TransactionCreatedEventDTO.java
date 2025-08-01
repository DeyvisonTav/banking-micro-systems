package com.banking.transactionservice.infrastructure.messaging.dto;

import com.banking.transactionservice.domain.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionCreatedEventDTO(
        UUID transactionId,
        String senderDocumentNumber,
        String receiverDocumentNumber,
        Integer amount,
        String status,
        LocalDateTime occurredOn
) {} 
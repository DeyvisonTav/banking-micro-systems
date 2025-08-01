package com.banking.transactionservice.infrastructure.web.dto;

import com.banking.transactionservice.domain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String senderDocumentNumber,
        String receiverDocumentNumber,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {} 
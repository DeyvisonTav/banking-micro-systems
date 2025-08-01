package com.banking.accountservice.infrastructure.web.dto;

import com.banking.accountservice.domain.Enum.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String name,
        String email,
        BigDecimal balance,
        Document documentType,
        String documentNumber,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {} 
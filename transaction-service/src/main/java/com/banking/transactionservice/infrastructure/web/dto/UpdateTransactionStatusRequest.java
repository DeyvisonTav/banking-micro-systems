package com.banking.transactionservice.infrastructure.web.dto;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTransactionStatusRequest(
        @NotNull(message = "New status is required")
        TransactionStatus newStatus
) {} 
package com.banking.transactionservice.application.command;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateTransactionStatusCommand(
        @NotNull(message = "Transaction ID is required")
        UUID transactionId,
        
        @NotNull(message = "New status is required")
        TransactionStatus newStatus
) {} 
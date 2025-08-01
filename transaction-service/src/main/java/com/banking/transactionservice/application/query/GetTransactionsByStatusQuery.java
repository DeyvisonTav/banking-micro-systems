package com.banking.transactionservice.application.query;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public record GetTransactionsByStatusQuery(
        @NotNull(message = "Status is required")
        TransactionStatus status
) {} 
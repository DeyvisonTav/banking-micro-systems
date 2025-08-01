package com.banking.transactionservice.application.query;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetTransactionByIdQuery(
        @NotNull(message = "Transaction ID is required")
        UUID transactionId
) {} 
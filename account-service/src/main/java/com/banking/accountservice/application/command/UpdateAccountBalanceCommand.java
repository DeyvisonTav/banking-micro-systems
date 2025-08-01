package com.banking.accountservice.application.command;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateAccountBalanceCommand(
        @NotNull(message = "Account ID is required")
        UUID accountId,
        
        @NotNull(message = "New balance is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
        BigDecimal newBalance
) {} 
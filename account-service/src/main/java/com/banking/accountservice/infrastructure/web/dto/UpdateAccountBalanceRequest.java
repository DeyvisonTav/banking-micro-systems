package com.banking.accountservice.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateAccountBalanceRequest(
        @NotNull(message = "New balance is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
        BigDecimal newBalance
) {} 
package com.banking.accountservice.application.query;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetAccountByIdQuery(
        @NotNull(message = "Account ID is required")
        UUID accountId
) {} 
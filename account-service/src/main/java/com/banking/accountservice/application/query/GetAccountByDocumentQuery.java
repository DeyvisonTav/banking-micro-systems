package com.banking.accountservice.application.query;

import jakarta.validation.constraints.NotBlank;

public record GetAccountByDocumentQuery(
        @NotBlank(message = "Document number is required")
        String documentNumber
) {} 
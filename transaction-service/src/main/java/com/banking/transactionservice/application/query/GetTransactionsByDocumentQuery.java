package com.banking.transactionservice.application.query;

import jakarta.validation.constraints.NotBlank;

public record GetTransactionsByDocumentQuery(
        @NotBlank(message = "Document number is required")
        String documentNumber
) {} 
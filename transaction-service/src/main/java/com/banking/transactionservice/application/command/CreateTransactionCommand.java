package com.banking.transactionservice.application.command;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateTransactionCommand(
        @NotBlank(message = "Sender document number is required")
        String senderDocumentNumber,
        
        @NotBlank(message = "Receiver document number is required")
        String receiverDocumentNumber,
        
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than zero")
        BigDecimal amount
) {} 
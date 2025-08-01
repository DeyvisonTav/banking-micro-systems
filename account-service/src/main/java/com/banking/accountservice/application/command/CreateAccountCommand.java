package com.banking.accountservice.application.command;

import com.banking.accountservice.domain.Enum.Document;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateAccountCommand(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,
        
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        
        @NotNull(message = "Initial balance is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Initial balance cannot be negative")
        BigDecimal initialBalance,
        
        @NotBlank(message = "Document number is required")
        String documentNumber,
        
        @NotNull(message = "Document type is required")
        Document documentType
) {} 
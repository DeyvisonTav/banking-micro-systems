package com.banking.accountservice.application.query;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GetAccountByEmailQuery(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) {} 
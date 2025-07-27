package com.banking.accountservice.domain.dto;

import com.banking.accountservice.domain.Enum.Document;

public record UpdateAccountDTO(
        String name,
        String email,
        Integer balance,
        Document documentType,
        String documentNumber
) {

    public UpdateAccountDTO {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (balance == null || balance < 0) {
            throw new IllegalArgumentException("Balance cannot be null or negative");
        }
        if (documentType == null) {
            throw new IllegalArgumentException("Document type cannot be null");
        }
        if (documentNumber == null || documentNumber.isBlank()) {
            throw new IllegalArgumentException("Document number cannot be null or blank");
        }
    }}

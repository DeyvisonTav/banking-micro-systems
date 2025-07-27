package com.banking.accountservice.domain.dto;

public record UpdateAccountDTO(
        String name,
        String email,
        Integer balance
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
    }}

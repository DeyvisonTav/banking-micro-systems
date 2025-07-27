package com.banking.accountservice.domain.dto;

public record CreateAccountDTO(
        String name,
        String email,
        Integer balance
) {}

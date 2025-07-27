package com.banking.accountservice.domain.dto;

import com.banking.accountservice.domain.Enum.Document;

public record CreateAccountDTO(
        String name,
        String email,
        Integer balance,
        Document documentType,
        String documentNumber
) {}

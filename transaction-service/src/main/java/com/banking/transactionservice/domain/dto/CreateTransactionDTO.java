package com.banking.transactionservice.domain.dto;

import java.util.UUID;

public record CreateTransactionDTO(
        String senderDocumentNumber,
        String receiverDocumentNumber,
        Integer amount
) {}
package com.banking.transactionservice.messaging.event;
import java.util.UUID;

public record TransactionCreatedEvent(
        UUID transactionId,
        String senderDocumentNumber,
        String receiverDocumentNumber,
        Integer amount,
        String status
) {
}

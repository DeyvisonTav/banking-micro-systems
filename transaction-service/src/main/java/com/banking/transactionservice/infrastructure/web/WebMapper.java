package com.banking.transactionservice.infrastructure.web;

import com.banking.transactionservice.application.command.CreateTransactionCommand;
import com.banking.transactionservice.application.command.UpdateTransactionStatusCommand;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.infrastructure.web.dto.CreateTransactionRequest;
import com.banking.transactionservice.infrastructure.web.dto.TransactionResponse;
import com.banking.transactionservice.infrastructure.web.dto.UpdateTransactionStatusRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WebMapper {
    
    public CreateTransactionCommand toCommand(CreateTransactionRequest request) {
        return new CreateTransactionCommand(
                request.senderDocumentNumber(),
                request.receiverDocumentNumber(),
                request.amount()
        );
    }
    
    public UpdateTransactionStatusCommand toCommand(UUID transactionId, UpdateTransactionStatusRequest request) {
        return new UpdateTransactionStatusCommand(transactionId, request.newStatus());
    }
    
    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId().value(),
                transaction.getSenderDocumentNumber().value(),
                transaction.getReceiverDocumentNumber().value(),
                transaction.getAmount().value(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
} 
package com.banking.transactionservice.application.handler;

import com.banking.transactionservice.application.command.UpdateTransactionStatusCommand;
import com.banking.transactionservice.application.port.TransactionEventPublisher;
import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.event.TransactionStatusChangedEvent;
import com.banking.transactionservice.domain.exception.TransactionNotFoundException;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionId;
import com.banking.transactionservice.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateTransactionStatusHandler {
    
    private final TransactionRepository transactionRepository;
    private final TransactionEventPublisher eventPublisher;
    
    public UpdateTransactionStatusHandler(TransactionRepository transactionRepository,
                                        TransactionEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Transactional
    public Transaction handle(UpdateTransactionStatusCommand command) {
        TransactionId transactionId = TransactionId.of(command.transactionId());
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
        
        TransactionStatus previousStatus = transaction.getStatus();
        
        switch (command.newStatus()) {
            case PROCESSING -> transaction.markAsProcessing();
            case COMPLETED -> transaction.markAsCompleted();
            case FAILED -> transaction.markAsFailed();
            case CANCELLED -> transaction.cancel();
            default -> throw new IllegalArgumentException("Unsupported status transition: " + command.newStatus());
        }
        
        Transaction updatedTransaction = transactionRepository.save(transaction);
        
        TransactionStatusChangedEvent event = new TransactionStatusChangedEvent(
                transactionId, previousStatus, command.newStatus(), LocalDateTime.now()
        );
        eventPublisher.publish(event);
        
        return updatedTransaction;
    }
} 
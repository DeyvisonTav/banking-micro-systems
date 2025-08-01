package com.banking.transactionservice.application.handler;

import com.banking.transactionservice.application.command.CreateTransactionCommand;
import com.banking.transactionservice.application.port.TransactionEventPublisher;
import com.banking.transactionservice.domain.event.TransactionCreatedEvent;
import com.banking.transactionservice.domain.model.Amount;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTransactionHandler {
    
    private final TransactionRepository transactionRepository;
    private final TransactionEventPublisher eventPublisher;
    
    public CreateTransactionHandler(TransactionRepository transactionRepository,
                                   TransactionEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Transactional
    public Transaction handle(CreateTransactionCommand command) {
        Transaction transaction = Transaction.create(
                command.senderDocumentNumber(),
                command.receiverDocumentNumber(),
                Amount.of(command.amount())
        );
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        TransactionCreatedEvent event = TransactionCreatedEvent.from(savedTransaction);
        eventPublisher.publish(event);
        
        return savedTransaction;
    }
} 
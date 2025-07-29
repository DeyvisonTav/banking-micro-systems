package com.banking.transactionservice.service;

import com.banking.transactionservice.domain.dto.CreateTransactionDTO;
import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.repository.TransactionRepository;
import com.banking.transactionservice.domain.entity.Transaction;
import com.banking.transactionservice.messaging.event.TransactionCreatedEvent;
import com.banking.transactionservice.messaging.event.TransactionEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionEventPublisher transactionEventPublisher;

    public TransactionService(TransactionRepository transactionRepository, TransactionEventPublisher transactionEventPublisher) {
        this.transactionRepository = transactionRepository;
        this.transactionEventPublisher =  transactionEventPublisher;
    }

    public Transaction createTransaction(CreateTransactionDTO createTransactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setSenderDocumentNumber(createTransactionDTO.senderDocumentNumber());
        transaction.setReceiverDocumentNumber(createTransactionDTO.receiverDocumentNumber());
        transaction.setAmount(createTransactionDTO.amount());
        transaction.setStatus(TransactionStatus.PENDING);

        Transaction saved = transactionRepository.save(transaction);

        TransactionCreatedEvent event = new TransactionCreatedEvent(
                saved.getId(),
                saved.getSenderDocumentNumber(),
                saved.getReceiverDocumentNumber(),
                saved.getAmount(),
                saved.getStatus().name()
        );

        transactionEventPublisher.publish(event);

        return saved;
    }
}

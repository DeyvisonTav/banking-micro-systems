package com.banking.transactionservice.domain.repository;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.model.DocumentNumber;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    
    Transaction save(Transaction transaction);
    
    Optional<Transaction> findById(TransactionId transactionId);
    
    List<Transaction> findAll();
    
    List<Transaction> findByStatus(TransactionStatus status);
    
    List<Transaction> findBySenderDocumentNumber(DocumentNumber senderDocumentNumber);
    
    List<Transaction> findByReceiverDocumentNumber(DocumentNumber receiverDocumentNumber);
    
    List<Transaction> findByDocumentNumber(DocumentNumber documentNumber);
    
    List<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    void delete(Transaction transaction);
}

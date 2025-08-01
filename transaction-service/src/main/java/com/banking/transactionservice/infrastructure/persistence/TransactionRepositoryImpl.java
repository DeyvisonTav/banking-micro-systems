package com.banking.transactionservice.infrastructure.persistence;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.model.DocumentNumber;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionId;
import com.banking.transactionservice.domain.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    
    private final JpaTransactionRepository jpaRepository;
    private final TransactionMapper mapper;
    
    public TransactionRepositoryImpl(JpaTransactionRepository jpaRepository, TransactionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = mapper.toEntity(transaction);
        TransactionEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Transaction> findById(TransactionId transactionId) {
        return jpaRepository.findById(transactionId.value())
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Transaction> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Transaction> findByStatus(TransactionStatus status) {
        return jpaRepository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Transaction> findBySenderDocumentNumber(DocumentNumber senderDocumentNumber) {
        return jpaRepository.findBySenderDocumentNumber(senderDocumentNumber.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Transaction> findByReceiverDocumentNumber(DocumentNumber receiverDocumentNumber) {
        return jpaRepository.findByReceiverDocumentNumber(receiverDocumentNumber.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Transaction> findByDocumentNumber(DocumentNumber documentNumber) {
        return jpaRepository.findByDocumentNumber(documentNumber.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public void delete(Transaction transaction) {
        TransactionEntity entity = mapper.toEntity(transaction);
        jpaRepository.delete(entity);
    }
} 
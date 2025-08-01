package com.banking.transactionservice.infrastructure.persistence;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    
    List<TransactionEntity> findByStatus(TransactionStatus status);
    
    List<TransactionEntity> findBySenderDocumentNumber(String senderDocumentNumber);
    
    List<TransactionEntity> findByReceiverDocumentNumber(String receiverDocumentNumber);
    
    @Query("SELECT t FROM TransactionEntity t WHERE t.senderDocumentNumber = :documentNumber OR t.receiverDocumentNumber = :documentNumber")
    List<TransactionEntity> findByDocumentNumber(@Param("documentNumber") String documentNumber);
    
    List<TransactionEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
} 
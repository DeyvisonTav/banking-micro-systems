package com.banking.transactionservice.domain.model;

import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.exception.InvalidTransactionException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    
    private final TransactionId id;
    private final DocumentNumber senderDocumentNumber;
    private final DocumentNumber receiverDocumentNumber;
    private final Amount amount;
    private TransactionStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Transaction(TransactionId id, DocumentNumber senderDocumentNumber, 
                       DocumentNumber receiverDocumentNumber, Amount amount) {
        this.id = Objects.requireNonNull(id, "Transaction ID cannot be null");
        this.senderDocumentNumber = Objects.requireNonNull(senderDocumentNumber, "Sender document number cannot be null");
        this.receiverDocumentNumber = Objects.requireNonNull(receiverDocumentNumber, "Receiver document number cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.status = TransactionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        
        validateTransaction();
    }
    
    public Transaction(TransactionId id, DocumentNumber senderDocumentNumber, 
                      DocumentNumber receiverDocumentNumber, Amount amount, 
                      TransactionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.senderDocumentNumber = senderDocumentNumber;
        this.receiverDocumentNumber = receiverDocumentNumber;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public static Transaction create(String senderDocument, String receiverDocument, Amount amount) {
        return new Transaction(
            TransactionId.generate(),
            DocumentNumber.of(senderDocument),
            DocumentNumber.of(receiverDocument),
            amount
        );
    }
    
    public void markAsProcessing() {
        changeStatus(TransactionStatus.PROCESSING);
    }
    
    public void markAsCompleted() {
        changeStatus(TransactionStatus.COMPLETED);
    }
    
    public void markAsFailed() {
        changeStatus(TransactionStatus.FAILED);
    }
    
    public void cancel() {
        changeStatus(TransactionStatus.CANCELLED);
    }
    
    private void changeStatus(TransactionStatus newStatus) {
        if (!this.status.canTransitionTo(newStatus)) {
            throw new InvalidTransactionException(
                "Cannot transition from " + this.status + " to " + newStatus
            );
        }
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isPending() {
        return this.status == TransactionStatus.PENDING;
    }
    
    public boolean isCompleted() {
        return this.status == TransactionStatus.COMPLETED;
    }
    
    public boolean isFailed() {
        return this.status == TransactionStatus.FAILED;
    }
    
    public boolean isTerminal() {
        return this.status.isTerminal();
    }
    
    private void validateTransaction() {
        if (senderDocumentNumber.equals(receiverDocumentNumber)) {
            throw new InvalidTransactionException("Sender and receiver cannot be the same");
        }
    }
    
    public TransactionId getId() {
        return id;
    }
    
    public DocumentNumber getSenderDocumentNumber() {
        return senderDocumentNumber;
    }
    
    public DocumentNumber getReceiverDocumentNumber() {
        return receiverDocumentNumber;
    }
    
    public Amount getAmount() {
        return amount;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderDocumentNumber=" + senderDocumentNumber +
                ", receiverDocumentNumber=" + receiverDocumentNumber +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
} 
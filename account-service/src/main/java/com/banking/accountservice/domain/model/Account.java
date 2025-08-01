package com.banking.accountservice.domain.model;

import com.banking.accountservice.domain.exception.InsufficientFundsException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Account {
    
    private final AccountId id;
    private final String name;
    private final Email email;
    private Balance balance;
    private final DocumentNumber documentNumber;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Account(AccountId id, String name, Email email, Balance initialBalance,
                   DocumentNumber documentNumber) {
        this.id = Objects.requireNonNull(id, "Account ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.balance = Objects.requireNonNull(initialBalance, "Balance cannot be null");
        this.documentNumber = Objects.requireNonNull(documentNumber, "Document number cannot be null");
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        
        validateName(name);
    }
    
    public Account(AccountId id, String name, Email email, Balance balance,
                   DocumentNumber documentNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.documentNumber = documentNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public static Account create(String name, String email, Balance initialBalance,
                                String documentValue, com.banking.accountservice.domain.Enum.Document documentType) {
        return new Account(
            AccountId.generate(),
            name,
            Email.of(email),
            initialBalance,
            DocumentNumber.of(documentValue, documentType)
        );
    }
    
    public void debit(Balance amount) {
        Objects.requireNonNull(amount, "Debit amount cannot be null");
        
        if (!this.balance.isGreaterThanOrEqual(amount)) {
            throw new InsufficientFundsException(this.balance, amount);
        }
        
        this.balance = this.balance.subtract(amount);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void credit(Balance amount) {
        Objects.requireNonNull(amount, "Credit amount cannot be null");
        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateBalance(Balance newBalance) {
        Objects.requireNonNull(newBalance, "New balance cannot be null");
        this.balance = newBalance;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean hasSufficientFunds(Balance requiredAmount) {
        return this.balance.isGreaterThanOrEqual(requiredAmount);
    }
    
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.trim().length() < 2) {
            throw new IllegalArgumentException("Name must have at least 2 characters");
        }
        if (name.trim().length() > 100) {
            throw new IllegalArgumentException("Name cannot exceed 100 characters");
        }
    }
    
    public AccountId getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public Balance getBalance() {
        return balance;
    }
    
    public DocumentNumber getDocumentNumber() {
        return documentNumber;
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
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email=" + email +
                ", balance=" + balance +
                ", documentNumber=" + documentNumber +
                '}';
    }
} 
package com.banking.transactionservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.transactionservice.domain.entity.Transaction;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}

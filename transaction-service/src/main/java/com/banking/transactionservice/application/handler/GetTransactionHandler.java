package com.banking.transactionservice.application.handler;

import com.banking.transactionservice.application.query.*;
import com.banking.transactionservice.domain.exception.TransactionNotFoundException;
import com.banking.transactionservice.domain.model.DocumentNumber;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionId;
import com.banking.transactionservice.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetTransactionHandler {
    
    private final TransactionRepository transactionRepository;
    
    public GetTransactionHandler(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    public Transaction handle(GetTransactionByIdQuery query) {
        TransactionId transactionId = TransactionId.of(query.transactionId());
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
    }
    
    public List<Transaction> handle(GetTransactionsByStatusQuery query) {
        return transactionRepository.findByStatus(query.status());
    }
    
    public List<Transaction> handle(GetTransactionsByDocumentQuery query) {
        DocumentNumber documentNumber = DocumentNumber.of(query.documentNumber());
        return transactionRepository.findByDocumentNumber(documentNumber);
    }
    
    public List<Transaction> handle(GetAllTransactionsQuery query) {
        return transactionRepository.findAll();
    }
} 
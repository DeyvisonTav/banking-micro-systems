package com.banking.transactionservice.controller;

import com.banking.transactionservice.domain.entity.Transaction;
import com.banking.transactionservice.service.TransactionService;
import com.banking.transactionservice.domain.dto.CreateTransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) {
        return transactionService.createTransaction(createTransactionDTO);
    }
}
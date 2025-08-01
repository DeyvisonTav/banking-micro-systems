package com.banking.transactionservice.infrastructure.web;

import com.banking.transactionservice.application.command.CreateTransactionCommand;
import com.banking.transactionservice.application.handler.CreateTransactionHandler;
import com.banking.transactionservice.application.handler.GetTransactionHandler;
import com.banking.transactionservice.application.handler.UpdateTransactionStatusHandler;
import com.banking.transactionservice.application.query.*;
import com.banking.transactionservice.domain.enums.TransactionStatus;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.infrastructure.web.WebMapper;
import com.banking.transactionservice.infrastructure.web.dto.CreateTransactionRequest;
import com.banking.transactionservice.infrastructure.web.dto.TransactionResponse;
import com.banking.transactionservice.infrastructure.web.dto.UpdateTransactionStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final CreateTransactionHandler createTransactionHandler;
    private final GetTransactionHandler getTransactionHandler;
    private final UpdateTransactionStatusHandler updateTransactionStatusHandler;
    private final WebMapper webMapper;

    public TransactionController(CreateTransactionHandler createTransactionHandler,
                               GetTransactionHandler getTransactionHandler,
                               UpdateTransactionStatusHandler updateTransactionStatusHandler,
                               WebMapper webMapper) {
        this.createTransactionHandler = createTransactionHandler;
        this.getTransactionHandler = getTransactionHandler;
        this.updateTransactionStatusHandler = updateTransactionStatusHandler;
        this.webMapper = webMapper;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        CreateTransactionCommand command = webMapper.toCommand(request);
        Transaction transaction = createTransactionHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(webMapper.toResponse(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id) {
        Transaction transaction = getTransactionHandler.handle(new GetTransactionByIdQuery(id));
        return ResponseEntity.ok(webMapper.toResponse(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<Transaction> transactions = getTransactionHandler.handle(new GetAllTransactionsQuery());
        List<TransactionResponse> responses = transactions.stream()
                .map(webMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        List<Transaction> transactions = getTransactionHandler.handle(new GetTransactionsByStatusQuery(status));
        List<TransactionResponse> responses = transactions.stream()
                .map(webMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByDocument(@PathVariable String documentNumber) {
        List<Transaction> transactions = getTransactionHandler.handle(new GetTransactionsByDocumentQuery(documentNumber));
        List<TransactionResponse> responses = transactions.stream()
                .map(webMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TransactionResponse> updateTransactionStatus(@PathVariable UUID id,
                                                                     @Valid @RequestBody UpdateTransactionStatusRequest request) {
        var command = webMapper.toCommand(id, request);
        Transaction transaction = updateTransactionStatusHandler.handle(command);
        return ResponseEntity.ok(webMapper.toResponse(transaction));
    }
}
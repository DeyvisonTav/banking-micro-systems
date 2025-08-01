package com.banking.accountservice.infrastructure.web;

import com.banking.accountservice.application.command.CreateAccountCommand;
import com.banking.accountservice.application.handler.CreateAccountHandler;
import com.banking.accountservice.application.handler.GetAccountHandler;
import com.banking.accountservice.application.handler.UpdateAccountBalanceHandler;
import com.banking.accountservice.application.query.*;
import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.infrastructure.web.WebMapper;
import com.banking.accountservice.infrastructure.web.dto.AccountResponse;
import com.banking.accountservice.infrastructure.web.dto.CreateAccountRequest;
import com.banking.accountservice.infrastructure.web.dto.UpdateAccountBalanceRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CreateAccountHandler createAccountHandler;
    private final GetAccountHandler getAccountHandler;
    private final UpdateAccountBalanceHandler updateAccountBalanceHandler;
    private final WebMapper webMapper;

    public AccountController(CreateAccountHandler createAccountHandler,
                           GetAccountHandler getAccountHandler,
                           UpdateAccountBalanceHandler updateAccountBalanceHandler,
                           WebMapper webMapper) {
        this.createAccountHandler = createAccountHandler;
        this.getAccountHandler = getAccountHandler;
        this.updateAccountBalanceHandler = updateAccountBalanceHandler;
        this.webMapper = webMapper;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<Account> accounts = getAccountHandler.handle(new GetAllAccountsQuery());
        List<AccountResponse> responses = accounts.stream()
                .map(webMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<AccountResponse> getAccountByDocument(@PathVariable String documentNumber) {
        Account account = getAccountHandler.handle(new GetAccountByDocumentQuery(documentNumber));
        return ResponseEntity.ok(webMapper.toResponse(account));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        CreateAccountCommand command = webMapper.toCommand(request);
        Account account = createAccountHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(webMapper.toResponse(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable UUID id) {
        Account account = getAccountHandler.handle(new GetAccountByIdQuery(id));
        return ResponseEntity.ok(webMapper.toResponse(account));
    }

    @GetMapping("/email")
    public ResponseEntity<AccountResponse> getByEmail(@RequestParam String email) {
        Account account = getAccountHandler.handle(new GetAccountByEmailQuery(email));
        return ResponseEntity.ok(webMapper.toResponse(account));
    }

    @PutMapping("/{id}/balance")
    public ResponseEntity<AccountResponse> updateAccountBalance(@PathVariable UUID id, 
                                                              @Valid @RequestBody UpdateAccountBalanceRequest request) {
        var command = webMapper.toCommand(id, request);
        Account account = updateAccountBalanceHandler.handle(command);
        return ResponseEntity.ok(webMapper.toResponse(account));
    }
}
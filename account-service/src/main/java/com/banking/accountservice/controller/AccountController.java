package com.banking.accountservice.controller;
import com.banking.accountservice.domain.dto.CreateAccountDTO;
import com.banking.accountservice.domain.dto.UpdateAccountDTO;
import com.banking.accountservice.domain.entity.Account;
import com.banking.accountservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<Account> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(accountService.findAccountByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountDTO dto) {
        return ResponseEntity.ok(accountService.updateAccount(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
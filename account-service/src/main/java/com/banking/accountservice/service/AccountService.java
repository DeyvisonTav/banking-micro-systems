package com.banking.accountservice.service;

import com.banking.accountservice.domain.dto.CreateAccountDTO;
import com.banking.accountservice.domain.dto.UpdateAccountDTO;
import com.banking.accountservice.domain.entity.Account;
import com.banking.accountservice.messaging.AccountEventPublisher;
import com.banking.accountservice.messaging.event.AccountCreatedEvent;
import com.banking.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountEventPublisher eventPublisher;

    public AccountService(AccountRepository accountRepository, AccountEventPublisher eventPublisher) {
        this.accountRepository = accountRepository;
        this.eventPublisher = eventPublisher;
    }

    public Account createAccount(CreateAccountDTO dto) {

        var existingAccountWithEmail = accountRepository.findByEmail(dto.email());
        var existingAccountWithDocument = accountRepository.findByDocumentNumber(dto.documentNumber());


        if (existingAccountWithEmail != null || existingAccountWithDocument != null) {
            throw new RuntimeException("Account already exists with the provided email or document number");
        }

        Account account = new Account(
                null,
                dto.name(),
                dto.email(),
                dto.balance(),
                dto.documentType(),
                dto.documentNumber()
        );

        Account saved = accountRepository.save(account);

        eventPublisher.publishAccountCreated(new AccountCreatedEvent(
                saved.getId(), saved.getName(), saved.getEmail(), saved.getBalance(), saved.getDocumentType(), saved.getDocumentNumber()
        ));

        return saved;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findAll().stream()
                .filter(account -> account.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void deleteAccount(UUID id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }


    public Account updateAccount(UUID id, UpdateAccountDTO updateAccountDTO) {
        Account existingAccount = getAccountById(id);
        Account updatedAccount = new Account(
                existingAccount.getId(),
                updateAccountDTO.name(),
                updateAccountDTO.email(),
                updateAccountDTO.balance(),
                updateAccountDTO.documentType(),
                updateAccountDTO.documentNumber()
        );
        return accountRepository.save(updatedAccount);
    }
}

package com.banking.accountservice.application.handler;

import com.banking.accountservice.application.query.GetAccountByDocumentQuery;
import com.banking.accountservice.application.query.GetAccountByEmailQuery;
import com.banking.accountservice.application.query.GetAccountByIdQuery;
import com.banking.accountservice.application.query.GetAllAccountsQuery;
import com.banking.accountservice.domain.exception.AccountNotFoundException;
import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.AccountId;
import com.banking.accountservice.domain.model.DocumentNumber;
import com.banking.accountservice.domain.model.Email;
import com.banking.accountservice.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetAccountHandler {
    
    private final AccountRepository accountRepository;
    
    public GetAccountHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Account handle(GetAccountByIdQuery query) {
        AccountId accountId = AccountId.of(query.accountId());
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
    
    public Account handle(GetAccountByEmailQuery query) {
        Email email = Email.of(query.email());
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with email: " + query.email()));
    }
    
    public Account handle(GetAccountByDocumentQuery query) {
        com.banking.accountservice.domain.Enum.Document documentType = 
                query.documentNumber().replaceAll("[^0-9]", "").length() == 11 ? 
                com.banking.accountservice.domain.Enum.Document.CPF : 
                com.banking.accountservice.domain.Enum.Document.CNPJ;
                
        DocumentNumber documentNumber = DocumentNumber.of(query.documentNumber(), documentType);
        return accountRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new AccountNotFoundException(query.documentNumber()));
    }
    
    public List<Account> handle(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }
} 
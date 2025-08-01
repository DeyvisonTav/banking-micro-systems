package com.banking.accountservice.application.handler;

import com.banking.accountservice.application.command.CreateAccountCommand;
import com.banking.accountservice.application.port.AccountEventPublisher;
import com.banking.accountservice.domain.event.AccountCreatedEvent;
import com.banking.accountservice.domain.exception.AccountAlreadyExistsException;
import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.Balance;
import com.banking.accountservice.domain.model.DocumentNumber;
import com.banking.accountservice.domain.model.Email;
import com.banking.accountservice.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateAccountHandler {
    
    private final AccountRepository accountRepository;
    private final AccountEventPublisher eventPublisher;
    
    public CreateAccountHandler(AccountRepository accountRepository, 
                               AccountEventPublisher eventPublisher) {
        this.accountRepository = accountRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Transactional
    public Account handle(CreateAccountCommand command) {
        Email email = Email.of(command.email());
        DocumentNumber documentNumber = DocumentNumber.of(command.documentNumber(), command.documentType());
        
        if (accountRepository.existsByEmail(email)) {
            throw new AccountAlreadyExistsException("email", command.email());
        }
        
        if (accountRepository.existsByDocumentNumber(documentNumber)) {
            throw new AccountAlreadyExistsException("document number", command.documentNumber());
        }
        
        Account account = Account.create(
                command.name(),
                command.email(),
                Balance.of(command.initialBalance()),
                command.documentNumber(),
                command.documentType()
        );
        
        Account savedAccount = accountRepository.save(account);
        
        AccountCreatedEvent event = AccountCreatedEvent.from(savedAccount);
        eventPublisher.publish(event);
        
        return savedAccount;
    }
} 
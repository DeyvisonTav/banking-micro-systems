package com.banking.accountservice.infrastructure.web;

import com.banking.accountservice.application.command.CreateAccountCommand;
import com.banking.accountservice.application.command.UpdateAccountBalanceCommand;
import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.infrastructure.web.dto.AccountResponse;
import com.banking.accountservice.infrastructure.web.dto.CreateAccountRequest;
import com.banking.accountservice.infrastructure.web.dto.UpdateAccountBalanceRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WebMapper {
    
    public CreateAccountCommand toCommand(CreateAccountRequest request) {
        return new CreateAccountCommand(
                request.name(),
                request.email(),
                request.initialBalance(),
                request.documentNumber(),
                request.documentType()
        );
    }
    
    public UpdateAccountBalanceCommand toCommand(UUID accountId, UpdateAccountBalanceRequest request) {
        return new UpdateAccountBalanceCommand(accountId, request.newBalance());
    }
    
    public AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId().value(),
                account.getName(),
                account.getEmail().value(),
                account.getBalance().amount(),
                account.getDocumentNumber().type(),
                account.getDocumentNumber().value(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
} 
package com.banking.accountservice.application.handler;

import com.banking.accountservice.application.command.UpdateAccountBalanceCommand;
import com.banking.accountservice.application.port.AccountEventPublisher;
import com.banking.accountservice.domain.event.AccountBalanceUpdatedEvent;
import com.banking.accountservice.domain.exception.AccountNotFoundException;
import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.AccountId;
import com.banking.accountservice.domain.model.Balance;
import com.banking.accountservice.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateAccountBalanceHandler {
    
    private final AccountRepository accountRepository;
    private final AccountEventPublisher eventPublisher;
    
    public UpdateAccountBalanceHandler(AccountRepository accountRepository,
                                     AccountEventPublisher eventPublisher) {
        this.accountRepository = accountRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Transactional
    public Account handle(UpdateAccountBalanceCommand command) {
        AccountId accountId = AccountId.of(command.accountId());
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        
        Balance previousBalance = account.getBalance();
        Balance newBalance = Balance.of(command.newBalance());
        
        account.updateBalance(newBalance);
        Account updatedAccount = accountRepository.save(account);
        
        AccountBalanceUpdatedEvent event = new AccountBalanceUpdatedEvent(
                accountId, previousBalance, newBalance, LocalDateTime.now()
        );
        eventPublisher.publish(event);
        
        return updatedAccount;
    }
} 
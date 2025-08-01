package com.banking.accountservice.infrastructure.persistence;

import com.banking.accountservice.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    
    public AccountEntity toEntity(Account account) {
        return new AccountEntity(
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
    
    public Account toDomain(AccountEntity entity) {
        return new Account(
                AccountId.of(entity.getId()),
                entity.getName(),
                Email.of(entity.getEmail()),
                Balance.of(entity.getBalance()),
                DocumentNumber.of(entity.getDocumentNumber(), entity.getDocumentType()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
} 
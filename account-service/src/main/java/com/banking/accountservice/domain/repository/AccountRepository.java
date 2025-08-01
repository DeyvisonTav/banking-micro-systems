package com.banking.accountservice.domain.repository;

import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.AccountId;
import com.banking.accountservice.domain.model.DocumentNumber;
import com.banking.accountservice.domain.model.Email;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    
    Account save(Account account);
    
    Optional<Account> findById(AccountId accountId);
    
    Optional<Account> findByEmail(Email email);
    
    Optional<Account> findByDocumentNumber(DocumentNumber documentNumber);
    
    List<Account> findAll();
    
    void delete(Account account);
    
    boolean existsByEmail(Email email);
    
    boolean existsByDocumentNumber(DocumentNumber documentNumber);
}

package com.banking.accountservice.infrastructure.persistence;

import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.AccountId;
import com.banking.accountservice.domain.model.DocumentNumber;
import com.banking.accountservice.domain.model.Email;
import com.banking.accountservice.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    
    private final JpaAccountRepository jpaRepository;
    private final AccountMapper mapper;
    
    public AccountRepositoryImpl(JpaAccountRepository jpaRepository, AccountMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Account save(Account account) {
        AccountEntity entity = mapper.toEntity(account);
        AccountEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Account> findById(AccountId accountId) {
        return jpaRepository.findById(accountId.value())
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Account> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.value())
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Account> findByDocumentNumber(DocumentNumber documentNumber) {
        return jpaRepository.findByDocumentNumber(documentNumber.value())
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Account> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public void delete(Account account) {
        AccountEntity entity = mapper.toEntity(account);
        jpaRepository.delete(entity);
    }
    
    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.value());
    }
    
    @Override
    public boolean existsByDocumentNumber(DocumentNumber documentNumber) {
        return jpaRepository.existsByDocumentNumber(documentNumber.value());
    }
} 
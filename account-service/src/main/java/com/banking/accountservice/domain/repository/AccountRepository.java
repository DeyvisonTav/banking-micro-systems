package com.banking.accountservice.domain.repository;

import com.banking.accountservice.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByEmail(String email);
    Account findByDocumentNumber(String documentNumber);
}

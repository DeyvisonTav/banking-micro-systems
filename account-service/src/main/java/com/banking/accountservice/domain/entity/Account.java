package com.banking.accountservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        private String name;
        private String email;
        private Integer balance;
}
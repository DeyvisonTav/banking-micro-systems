package com.banking.accountservice.domain.entity;

import com.banking.accountservice.domain.Enum.Document;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UniqueElements;

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
        @Column(unique = true)
        private String email;
        private Integer balance;
        @Column(name = "document_type")
        private Document documentType;
        @Column(name = "document_number", unique = true)
        private String documentNumber;
}
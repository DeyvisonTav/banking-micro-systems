package com.banking.transactionservice.domain.model;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public record DocumentNumber(String value) {
    
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");
    
    public DocumentNumber {
        Objects.requireNonNull(value, "Document number cannot be null");
        
        String cleanValue = value.replaceAll("[^0-9]", "");
        
        if (!CPF_PATTERN.matcher(cleanValue).matches() && !CNPJ_PATTERN.matcher(cleanValue).matches()) {
            throw new IllegalArgumentException("Invalid document format. Must be CPF (11 digits) or CNPJ (14 digits)");
        }
        
        value = cleanValue;
    }
    
    public static DocumentNumber of(String value) {
        return new DocumentNumber(value);
    }
    
    public boolean isCPF() {
        return value.length() == 11;
    }
    
    public boolean isCNPJ() {
        return value.length() == 14;
    }
} 
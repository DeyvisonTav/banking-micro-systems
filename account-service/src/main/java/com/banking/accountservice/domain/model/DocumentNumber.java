package com.banking.accountservice.domain.model;

import com.banking.accountservice.domain.Enum.Document;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public record DocumentNumber(String value, Document type) {
    
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");
    
    public DocumentNumber {
        Objects.requireNonNull(value, "Document number cannot be null");
        Objects.requireNonNull(type, "Document type cannot be null");
        
        String cleanValue = value.replaceAll("[^0-9]", "");
        
        switch (type) {
            case CPF -> {
                if (!CPF_PATTERN.matcher(cleanValue).matches()) {
                    throw new IllegalArgumentException("Invalid CPF format");
                }
                if (!isValidCPF(cleanValue)) {
                    throw new IllegalArgumentException("Invalid CPF number");
                }
            }
            case CNPJ -> {
                if (!CNPJ_PATTERN.matcher(cleanValue).matches()) {
                    throw new IllegalArgumentException("Invalid CNPJ format");
                }
                if (!isValidCNPJ(cleanValue)) {
                    throw new IllegalArgumentException("Invalid CNPJ number");
                }
            }
        }
        
        value = cleanValue;
    }
    
    public static DocumentNumber of(String value, Document type) {
        return new DocumentNumber(value, type);
    }
    
    private boolean isValidCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;
        
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;
        
        return Character.getNumericValue(cpf.charAt(9)) == firstDigit &&
               Character.getNumericValue(cpf.charAt(10)) == secondDigit;
    }
    
    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
        }
        int firstDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
        }
        int secondDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        
        return Character.getNumericValue(cnpj.charAt(12)) == firstDigit &&
               Character.getNumericValue(cnpj.charAt(13)) == secondDigit;
    }
} 
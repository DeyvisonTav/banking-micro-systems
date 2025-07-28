package com.banking.accountservice.exception;

public class NotFoundException extends  ApiException{
    public NotFoundException(String message) {
        super(message, 404);
    }
}

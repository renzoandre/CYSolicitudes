package com.bootcamp.usecase.exception;

public class LoanTypeNotFoundException extends RuntimeException {
    public LoanTypeNotFoundException(String message) {
        super(message);
    }
}
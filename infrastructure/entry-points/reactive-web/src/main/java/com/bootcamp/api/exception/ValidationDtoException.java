package com.bootcamp.api.exception;

public class ValidationDtoException extends RuntimeException {
    public ValidationDtoException(String message) {
        super(message);
    }
}
package com.bootcamp.r2dbc.exception;

public class DatabaseUnavailableException extends RuntimeException {
    public DatabaseUnavailableException(String message) {
        super(message);
    }
}
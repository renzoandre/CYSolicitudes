package com.bootcamp.usecase.exception;

public class StateApplicationNotFoundException extends RuntimeException {
    public StateApplicationNotFoundException(String message) {
        super(message);
    }
}
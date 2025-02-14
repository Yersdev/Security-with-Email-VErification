package com.example.demo.exception;

public class AccountHasAlreadyVerifiedException extends RuntimeException {
    public AccountHasAlreadyVerifiedException(String message) {
        super(message);
    }
}

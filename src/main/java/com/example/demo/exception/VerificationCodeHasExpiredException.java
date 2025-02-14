package com.example.demo.exception;

public class VerificationCodeHasExpiredException extends RuntimeException {
    public VerificationCodeHasExpiredException(String message) {
        super(message);
    }
}

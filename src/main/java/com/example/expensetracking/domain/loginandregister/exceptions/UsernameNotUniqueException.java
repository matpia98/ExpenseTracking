package com.example.expensetracking.domain.loginandregister.exceptions;

public class UsernameNotUniqueException extends RuntimeException {
    public UsernameNotUniqueException(String message) {
        super(message);
    }
}

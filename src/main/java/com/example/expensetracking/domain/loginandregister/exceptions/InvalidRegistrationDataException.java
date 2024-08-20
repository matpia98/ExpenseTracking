package com.example.expensetracking.domain.loginandregister.exceptions;

public class InvalidRegistrationDataException extends RuntimeException {
    public InvalidRegistrationDataException(String message) {
        super(message);
    }
}

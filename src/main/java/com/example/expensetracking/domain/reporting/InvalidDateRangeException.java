package com.example.expensetracking.domain.reporting;

public class InvalidDateRangeException extends IllegalArgumentException {
    public InvalidDateRangeException(String message) {
        super(message);
    }
}

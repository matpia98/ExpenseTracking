package com.example.expensetracking.domain.budgeting;

public class NoBudgetsFoundException extends RuntimeException {
    public NoBudgetsFoundException(String message) {
        super(message);
    }
}

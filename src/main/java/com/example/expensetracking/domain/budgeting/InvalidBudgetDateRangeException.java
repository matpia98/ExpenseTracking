package com.example.expensetracking.domain.budgeting;

public class InvalidBudgetDateRangeException extends IllegalArgumentException {
    public InvalidBudgetDateRangeException(String message) {
        super(message);
    }
}

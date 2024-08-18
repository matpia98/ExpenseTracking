package com.example.expensetracking.domain.crud;

public class CategoryHasExpensesException extends RuntimeException {
    public CategoryHasExpensesException(String message) {
        super(message);
    }
}

package com.example.expensetracking.domain.crud;

class CategoryHasExpensesException extends RuntimeException {
    public CategoryHasExpensesException(String message) {
        super(message);
    }
}

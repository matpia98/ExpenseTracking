package com.example.expensetracking.domain.category.crud;

class CategoryHasExpensesException extends RuntimeException {
    public CategoryHasExpensesException(String message) {
        super(message);
    }
}

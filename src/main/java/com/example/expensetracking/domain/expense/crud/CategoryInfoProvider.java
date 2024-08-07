package com.example.expensetracking.domain.expense.crud;

public interface CategoryInfoProvider {
    String getCategoryNameById(Long categoryId);
    boolean categoryExists(Long categoryId);
}
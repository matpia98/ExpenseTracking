package com.example.expensetracking.infrastructure.crud.controller.expense.dto;

public record UpdateExpenseResponseDto(
        Long id,
        String title,
        String description,
        double amount,
        String date,
        Long categoryId
) {
}

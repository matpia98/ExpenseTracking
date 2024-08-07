package com.example.expensetracking.infrastructure.expense.controller.dto;

import lombok.Builder;

@Builder
public record CreateExpenseResponseDto(
        Long id,
        String title,
        String description,
        double amount,
        String date,
        Long categoryId,
        String categoryName
) {
}

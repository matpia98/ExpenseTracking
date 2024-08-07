package com.example.expensetracking.domain.expense.crud.dto;

import lombok.Builder;

@Builder
public record ExpenseResponseDto(
        Long id,
        String title,
        String description,
        double amount,
        String category,
        String date,
        Long categoryId
) {
}

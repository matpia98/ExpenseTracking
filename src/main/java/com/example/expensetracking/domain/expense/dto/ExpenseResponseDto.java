package com.example.expensetracking.domain.expense.dto;

import lombok.Builder;

@Builder
public record ExpenseResponseDto(
        Long id,
        String title,
        String description,
        double amount,
        String category,
        String date
) {
}

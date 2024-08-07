package com.example.expensetracking.domain.expense.dto;

import lombok.Builder;

@Builder
public record ExpenseRequestDto(
        String title,
        String description,
        double amount,
        String category,
        String date
) {
}

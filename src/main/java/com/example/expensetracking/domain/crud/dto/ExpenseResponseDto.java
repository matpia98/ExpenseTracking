package com.example.expensetracking.domain.crud.dto;

import lombok.Builder;

@Builder
public record ExpenseResponseDto(
        Long id,
        String title,
        String description,
        double amount,
        String date,
        Long categoryId
) {}

package com.example.expensetracking.domain.crud.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExpenseResponseDto(
        Long id,
        String title,
        String description,
        double amount,
        LocalDateTime date,
        Long categoryId,
        String categoryName
) {}

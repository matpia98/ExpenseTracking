package com.example.expensetracking.domain.budgeting.dto;

import java.time.LocalDate;
import java.util.List;

public record BudgetResponseDto(
        Long id,
        LocalDate startDate,
        LocalDate endDate,
        List<CategoryDto> categories
) {
}
package com.example.expensetracking.domain.budgeting.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record BudgetRequestDto(
        @NotNull(message = "Start date is required")
        @FutureOrPresent(message = "Start date must be today or in the future")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        @FutureOrPresent(message = "End date must be today or in the future")
        LocalDate endDate,

        @NotNull(message = "Categories list is required")
        @Size(min = 1, message = "At least one category is required")
        List<CategoryDto> categories
) {
}

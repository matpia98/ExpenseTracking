package com.example.expensetracking.infrastructure.crud.controller.category.dto;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;

import java.util.List;

public record GetExpensesByCategoryResponseDto(
        List<ExpenseResponseDto> expenses
) {
}

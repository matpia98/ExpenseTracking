package com.example.expensetracking.infrastructure.crud.controller.expense.dto;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;

import java.util.List;

public record GetAllExpensesResponseDto(
        List<ExpenseResponseDto> expenses
) {
}

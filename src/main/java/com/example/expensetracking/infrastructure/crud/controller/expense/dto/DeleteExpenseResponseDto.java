package com.example.expensetracking.infrastructure.crud.controller.expense.dto;

import org.springframework.http.HttpStatus;

public record DeleteExpenseResponseDto(
        String message,
        HttpStatus status
) {
}

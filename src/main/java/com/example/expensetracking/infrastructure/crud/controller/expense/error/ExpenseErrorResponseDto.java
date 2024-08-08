package com.example.expensetracking.infrastructure.crud.controller.expense.error;

import org.springframework.http.HttpStatus;

record ExpenseErrorResponseDto(
        String message,
        HttpStatus status
) {
}

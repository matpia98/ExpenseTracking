package com.example.expensetracking.infrastructure.budgeting.controller.error;

import org.springframework.http.HttpStatus;

record BudgetErrorResponseDto(
        String message,
        HttpStatus status
) {
}

package com.example.expensetracking.infrastructure.crud.controller.category.error;

import org.springframework.http.HttpStatus;

record CategoryErrorResponseDto(
        String message,
        HttpStatus status
) {
}

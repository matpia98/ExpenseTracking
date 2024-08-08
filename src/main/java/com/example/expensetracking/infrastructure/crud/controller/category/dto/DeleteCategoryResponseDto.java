package com.example.expensetracking.infrastructure.crud.controller.category.dto;

import org.springframework.http.HttpStatus;

public record DeleteCategoryResponseDto(
        String message,
        HttpStatus status
) {
}

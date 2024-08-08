package com.example.expensetracking.infrastructure.crud.controller.category.dto;

import lombok.Builder;

@Builder
public record GetCategoryResponseDto(
        Long id,
        String name
) {
}

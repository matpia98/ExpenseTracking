package com.example.expensetracking.domain.category.crud.dto;

import lombok.Builder;

@Builder
public record CategoryResponseDto(
        Long id,
        String name
) {
}

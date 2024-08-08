package com.example.expensetracking.domain.crud.dto;

import lombok.Builder;

@Builder
public record CategoryResponseDto(
        Long id,
        String name
) {
}

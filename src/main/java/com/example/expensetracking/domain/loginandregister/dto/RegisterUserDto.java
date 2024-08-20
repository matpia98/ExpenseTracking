package com.example.expensetracking.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterUserDto(
        String username,
        String password
) {
}

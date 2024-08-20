package com.example.expensetracking.domain.loginandregister.dto;

public record RegistrationResultDto(
        String id,
        String username,
        boolean created
) {
}
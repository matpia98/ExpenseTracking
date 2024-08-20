package com.example.expensetracking.infrastructure.loginandregister.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDto(
        @NotBlank(message = "username must not be blank")
        String username,

        @NotBlank(message = "password must not be blank")
        String password
) {
}

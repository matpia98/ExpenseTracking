package com.example.expensetracking.domain.budgeting.dto;

import java.math.BigDecimal;

public record CategoryDto(
        String name,
        BigDecimal budgetLimit
) {
}

package com.example.expensetracking.domain.budgeting.dto;

import java.math.BigDecimal;

public record CategorySummary(
        String name,
        BigDecimal limit,
        BigDecimal spent,
        BigDecimal remaining
) {
}

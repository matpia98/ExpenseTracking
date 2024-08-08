package com.example.expensetracking.domain.reporting.dto;

import java.math.BigDecimal;

public record CategorySummary(
        String categoryName,
        BigDecimal totalAmount,
        int totalExpenses
) {
}

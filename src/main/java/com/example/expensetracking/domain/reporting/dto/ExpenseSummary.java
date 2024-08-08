package com.example.expensetracking.domain.reporting.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseSummary(
        Long id,
        String title,
        BigDecimal amount,
        LocalDateTime date,
        String categoryName
) {
}

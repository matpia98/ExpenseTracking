package com.example.expensetracking.domain.budgeting.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BudgetExpenseDto(
        Long id,
        String title,
        BigDecimal amount,
        LocalDateTime date
) {
}

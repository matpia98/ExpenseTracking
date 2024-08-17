package com.example.expensetracking.domain.budgeting.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BudgetResponseDto(
        Long id,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal spent,
        BigDecimal remaining,
        List<BudgetExpenseDto> expenses
) {}
package com.example.expensetracking.domain.budgeting.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BudgetRequestDto(
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal initialAmount
) {}

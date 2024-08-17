package com.example.expensetracking.domain.budgeting.dto;

import java.math.BigDecimal;

public record AddExpenseToBudgetResponseDto(
        Long budgetId,
        Long expenseId,
        BigDecimal updatedSpent,
        BigDecimal updatedRemaining,
        BudgetExpenseDto addedExpense
) {}
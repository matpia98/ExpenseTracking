package com.example.expensetracking.infrastructure.budgeting.controller;

import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;

import java.util.List;

public record GetAllBudgetsResponseDto(List<BudgetSummaryDto> activeBudgets) {
}

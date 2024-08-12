package com.example.expensetracking.infrastructure.budgeting.controller;

import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;

import java.util.List;

class BudgetingControllerMapper {


    static GetAllBudgetsResponseDto mapToGetAllBudgetsResponseDto(List<BudgetSummaryDto> activeBudgets) {
        return new GetAllBudgetsResponseDto(activeBudgets);
    }
}

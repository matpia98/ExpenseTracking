package com.example.expensetracking.domain.budgeting.dto;

import java.time.LocalDate;
import java.util.List;

public record BudgetSummaryDto(
        LocalDate startDate,
        LocalDate endDate,
        List<CategorySummary> summary,
        List<BudgetExpenseDto> expenses

) {
}

package com.example.expensetracking.domain.reporting.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReportDto(
        LocalDateTime generationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal totalExpenses,
        List<CategorySummary> categorySummaries,
        List<ExpenseSummary> topExpenses
) {
}

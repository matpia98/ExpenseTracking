package com.example.expensetracking.domain.reporting;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.domain.reporting.dto.CategorySummary;
import com.example.expensetracking.domain.reporting.dto.ExpenseSummary;
import com.example.expensetracking.domain.reporting.dto.ReportDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class ReportGenerator {
    ReportDto generateReport(List<ExpenseResponseDto> expenses, LocalDate startDate, LocalDate endDate) {

        LocalDateTime generationDate = LocalDateTime.now();
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay().minusNanos(1);

        if (expenses.isEmpty()) {
            return new ReportDto(
                    generationDate,
                    startDateTime,
                    endDateTime,
                    BigDecimal.ZERO,
                    Collections.emptyList(),
                    Collections.emptyList()
            );
        }

        double totalExpenses = expenses.stream()
                .mapToDouble(ExpenseResponseDto::amount)
                .sum();
        BigDecimal totalExpensesBigDecimal = roundToCurrency(totalExpenses);

        Map<String, List<ExpenseResponseDto>> expensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(ExpenseResponseDto::categoryName));

        List<CategorySummary> categorySummaries = expensesByCategory.entrySet().stream()
                .map(entry -> {
                    String categoryName = entry.getKey();
                    List<ExpenseResponseDto> categoryExpenses = entry.getValue();
                    double categoryTotal = categoryExpenses.stream()
                            .mapToDouble(ExpenseResponseDto::amount)
                            .sum();
                    BigDecimal categoryTotalBigDecimal = roundToCurrency(categoryTotal);
                    return new CategorySummary(categoryName, categoryTotalBigDecimal, categoryExpenses.size());
                })
                .toList();

        List<ExpenseSummary> topExpenses = expenses.stream()
                .sorted((e1, e2) -> e2.amount().compareTo(e1.amount()))
                .limit(5)
                .map(e -> {
                    BigDecimal amount = roundToCurrency(e.amount());
                    return new ExpenseSummary(e.id(), e.title(), amount, e.date(), e.categoryName());
                })
                .toList();

        return new ReportDto(
                generationDate,
                startDateTime,
                endDateTime,
                totalExpensesBigDecimal,
                categorySummaries,
                topExpenses
        );
    }

    private BigDecimal roundToCurrency(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

}

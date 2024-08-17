package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.dto.BudgetExpenseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;
import com.example.expensetracking.domain.budgeting.dto.CategorySummary;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
class BudgetSummarizer {
    private final BudgetRepository budgetRepository;

    BudgetSummarizer(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    List<Budget> findActiveBudgets() {
        List<Budget> budgets = budgetRepository.findAllByEndDateAfter(LocalDate.now());
        if (budgets.isEmpty()) {
            throw new NoBudgetsFoundException("No active budgets found");
        }
        return budgets;
    }

    List<BudgetSummaryDto> getBudgetSummaries(List<Budget> budgets) {
        return budgets.stream()
                .map(this::getBudgetSummary)
                .collect(Collectors.toList());
    }

    private BudgetSummaryDto getBudgetSummary(Budget budget) {
        BigDecimal spent = calculateSpentAmount(budget);
        BigDecimal remaining = budget.getRemaining();
        BigDecimal limit = spent.add(remaining);

        CategorySummary categorySummary = new CategorySummary(
                "Total",
                limit,
                spent,
                remaining
        );

        List<BudgetExpenseDto> budgetExpenseDtos = budget.getExpenses().stream()
                .map(this::mapToBudgetExpenseDto)
                .collect(Collectors.toList());

        return new BudgetSummaryDto(
                budget.getStartDate(),
                budget.getEndDate(),
                List.of(categorySummary),
                budgetExpenseDtos
        );
    }

    private BigDecimal calculateSpentAmount(Budget budget) {
        return budget.getExpenses().stream()
                .map(BudgetExpense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BudgetExpenseDto mapToBudgetExpenseDto(BudgetExpense expense) {
        return new BudgetExpenseDto(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getDate()
        );
    }
}
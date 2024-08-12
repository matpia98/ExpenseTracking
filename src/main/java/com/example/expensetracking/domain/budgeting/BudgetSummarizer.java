package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.Budget;
import com.example.expensetracking.domain.budgeting.BudgetRepository;
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

    List<BudgetSummaryDto> getBudgetSummaries(List<Budget> budgets, List<ExpenseResponseDto> expenses) {
        return budgets.stream()
                .map(budget -> getBudgetSummary(budget, expenses))
                .collect(Collectors.toList());
    }

    private BudgetSummaryDto getBudgetSummary(Budget budget, List<ExpenseResponseDto> expenses) {
        List<CategorySummary> categorySummaries = budget.getCategories().stream()
                .map(category -> {
                    BigDecimal spent = ExpenseCalculator.calculateSpentAmount(expenses, category.getName(), budget.getStartDate(), budget.getEndDate());
                    return new CategorySummary(
                            category.getName(),
                            category.getBudgetLimit(),
                            spent,
                            category.getBudgetLimit().subtract(spent)
                    );
                })
                .collect(Collectors.toList());

        return new BudgetSummaryDto(budget.getStartDate(), budget.getEndDate(), categorySummaries);
    }
}

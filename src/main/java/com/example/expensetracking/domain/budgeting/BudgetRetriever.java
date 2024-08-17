package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.dto.BudgetExpenseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BudgetRetriever {
    private final BudgetRepository budgetRepository;

    BudgetRetriever(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public BudgetResponseDto getBudgetById(Long budgetId) {
        Budget budget = findBudgetById(budgetId);
        return mapToBudgetResponseDto(budget);
    }

    private Budget findBudgetById(Long budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget with id " + budgetId + " not found"));
    }

    private BudgetResponseDto mapToBudgetResponseDto(Budget budget) {
        List<BudgetExpenseDto> expenseDtos = budget.getExpenses().stream()
                .map(this::mapToBudgetExpenseDto)
                .toList();

        return new BudgetResponseDto(
                budget.getId(),
                budget.getStartDate(),
                budget.getEndDate(),
                budget.getSpent(),
                budget.getRemaining(),
                expenseDtos
        );
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
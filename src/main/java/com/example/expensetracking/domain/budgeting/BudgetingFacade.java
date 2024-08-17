package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.dto.AddExpenseToBudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetExpenseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetRequestDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;
import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class BudgetingFacade {
    private final BudgetCreator budgetCreator;
    private final BudgetSummarizer budgetSummarizer;
    private final BudgetExpenseAdder budgetExpenseAdder;
    private final BudgetRetriever budgetRetriever;
    private final ExpenseTrackingCrudFacade expenseTrackingCrudFacade;

    public BudgetResponseDto createBudget(BudgetRequestDto requestDto) {
        if (requestDto.endDate().isBefore(requestDto.startDate())) {
            throw new InvalidBudgetDateRangeException("End date must be after start date");
        }
        return budgetCreator.createBudget(requestDto);
    }

    public AddExpenseToBudgetResponseDto addExistingExpenseToBudget(Long budgetId, Long expenseId) {
        ExpenseResponseDto expense = expenseTrackingCrudFacade.getExpenseById(expenseId);
        return budgetExpenseAdder.addExpenseToBudget(budgetId, expense);
    }

    public List<BudgetSummaryDto> summarizeActiveBudgets() {
        List<Budget> activeBudgets = budgetSummarizer.findActiveBudgets();
        return budgetSummarizer.getBudgetSummaries(activeBudgets);
    }

    public BudgetResponseDto getBudgetById(Long budgetId) {
        return budgetRetriever.getBudgetById(budgetId);
    }

}

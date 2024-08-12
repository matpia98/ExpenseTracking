package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.Budget;
import com.example.expensetracking.domain.budgeting.BudgetCreator;
import com.example.expensetracking.domain.budgeting.BudgetSummarizer;
import com.example.expensetracking.domain.budgeting.dto.BudgetRequestDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;
import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.domain.reporting.InvalidDateRangeException;
import lombok.AllArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class BudgetingFacade {
    private final BudgetCreator budgetCreator;
    private final BudgetSummarizer budgetSummarizer;
    private final ExpenseTrackingCrudFacade expenseTrackingCrudFacade;

    public BudgetResponseDto createBudget(BudgetRequestDto requestDto) {
        if (requestDto.endDate().isBefore(requestDto.startDate())) {
            throw new InvalidBudgetDateRangeException("End date must be after start date");
        }
        return budgetCreator.createBudget(requestDto);
    }

    public List<BudgetSummaryDto> summarizeActiveBudgets() {
        List<Budget> activeBudgets = budgetSummarizer.findActiveBudgets();
        LocalDate startDate = activeBudgets.stream()
                .map(Budget::getStartDate)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());
        LocalDate endDate = activeBudgets.stream()
                .map(Budget::getEndDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());

        List<ExpenseResponseDto> expenses = expenseTrackingCrudFacade.getExpensesBetweenDates(startDate, endDate);
        return budgetSummarizer.getBudgetSummaries(activeBudgets, expenses);
    }
}

package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.dto.BudgetExpenseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetRequestDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
class BudgetCreator {
    private final BudgetRepository budgetRepository;

    BudgetCreator(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    BudgetResponseDto createBudget(BudgetRequestDto requestDto) {
        Budget budget = Budget.builder()
                .startDate(requestDto.startDate())
                .endDate(requestDto.endDate())
                .spent(BigDecimal.ZERO)
                .remaining(requestDto.initialAmount())
                .build();

        Budget savedBudget = budgetRepository.save(budget);
        return mapToBudgetResponseDto(savedBudget);
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

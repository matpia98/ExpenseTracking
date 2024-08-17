package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.dto.AddExpenseToBudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetExpenseDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
class BudgetExpenseAdder {
    private final BudgetRepository budgetRepository;

    BudgetExpenseAdder(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Transactional
    public AddExpenseToBudgetResponseDto addExpenseToBudget(Long budgetId, ExpenseResponseDto expenseDto) {
        Budget budget = findBudgetById(budgetId);

        // Check if the expense already exists in the budget
        BudgetExpense existingExpense = budget.getExpenses().stream()
                .filter(be -> be.getId().equals(expenseDto.id()))
                .findFirst()
                .orElse(null);

        if (existingExpense != null) {
            // Update existing expense
            existingExpense.setTitle(expenseDto.title());
            existingExpense.setAmount(BigDecimal.valueOf(expenseDto.amount()));
            existingExpense.setDate(expenseDto.date());
        } else {
            // Create new BudgetExpense
            BudgetExpense newExpense = createBudgetExpense(expenseDto);
            budget.addExpense(newExpense);
        }

        Budget savedBudget = budgetRepository.save(budget);
        BudgetExpense savedExpense = savedBudget.getExpenses().stream()
                .filter(be -> be.getId().equals(expenseDto.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to save expense"));

        return createResponse(savedBudget, savedExpense);
    }

    private Budget findBudgetById(Long budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget with id " + budgetId + " not found"));
    }

    private BudgetExpense createBudgetExpense(ExpenseResponseDto expenseDto) {
        return BudgetExpense.builder()
                .id(expenseDto.id())
                .title(expenseDto.title())
                .amount(BigDecimal.valueOf(expenseDto.amount()))
                .date(expenseDto.date())
                .build();
    }

    private AddExpenseToBudgetResponseDto createResponse(Budget budget, BudgetExpense expense) {
        return new AddExpenseToBudgetResponseDto(
                budget.getId(),
                expense.getId(),
                budget.getSpent(),
                budget.getRemaining(),
                mapToBudgetExpenseDto(expense)
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

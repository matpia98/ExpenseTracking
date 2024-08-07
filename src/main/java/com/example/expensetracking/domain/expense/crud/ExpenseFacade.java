package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ExpenseFacade {

    private final ExpenseAdder expenseAdder;
    private final ExpenseRetriever expenseRetriever;
    private final ExpenseDeleter expenseDeleter;
    private final ExpenseUpdater expenseUpdater;

    public ExpenseResponseDto addExpense(ExpenseRequestDto expenseRequestDto) {
        return expenseAdder.addExpense(expenseRequestDto);
    }

    public List<ExpenseResponseDto> getAllExpenses() {
        return expenseRetriever.getAllExpenses();
    }

    public ExpenseResponseDto getExpenseById(Long id) {
        return expenseRetriever.getExpenseById(id);
    }

    @Transactional
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        return expenseUpdater.updateExpense(id, expenseRequestDto);
    }

    public void deleteExpense(Long id) {
        expenseDeleter.deleteById(id);
    }

    public List<ExpenseResponseDto> getExpensesByCategoryId(Long categoryId) {
        return expenseRetriever.getExpensesByCategoryId(categoryId);
    }
}
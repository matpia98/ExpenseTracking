package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class ExpenseRetriever {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    ExpenseRetriever(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }

    ExpenseResponseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with id: " + id + " not found"));
        return expenseMapper.mapFromExpenseToExpenseResponse(expense);
    }

    List<ExpenseResponseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            throw new ExpenseNotFoundException("No expenses found");
        }
        return expenses.stream()
                .map(expenseMapper::mapFromExpenseToExpenseResponse)
                .collect(Collectors.toList());
    }

    List<ExpenseResponseDto> getExpensesByCategoryId(Long categoryId) {
        List<Expense> expenses = expenseRepository.findAllByCategoryId(categoryId);
        if (expenses.isEmpty()) {
            throw new ExpenseNotFoundException("No expenses found");
        }
        return expenses.stream()
                .map(expenseMapper::mapFromExpenseToExpenseResponse)
                .collect(Collectors.toList());
    }

}
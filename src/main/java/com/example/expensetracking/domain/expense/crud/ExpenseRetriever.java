package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.dto.ExpenseResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ExpenseRetriever {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    ExpenseResponseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with id: " + id + " not found"));
        return expenseMapper.mapFromExponseToExponseResponse(expense);
    }

    List<ExpenseResponseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            throw new ExpenseNotFoundException("No expenses found");
        }
        return expenses.stream()
                .map(expenseMapper::mapFromExponseToExponseResponse)
                .collect(Collectors.toList());
    }
}
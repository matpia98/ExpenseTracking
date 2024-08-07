package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Service;

@Service
class ExpenseUpdater {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    ExpenseUpdater(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }


    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        Expense expense = expenseMapper.mapFromExpenseRequestToExpense(expenseRequestDto);
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Expense with id " + id + " not found");
        }
        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.mapFromExpenseToExpenseResponse(savedExpense);

    }
}
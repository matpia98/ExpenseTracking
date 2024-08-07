package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;

import org.springframework.stereotype.Service;

@Service
class ExpenseAdder {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    ExpenseAdder(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }

    ExpenseResponseDto addExpense(ExpenseRequestDto expenseRequestDto) {
        Expense expense = expenseMapper.mapFromExpenseRequestToExpense(expenseRequestDto);
        expenseRepository.save(expense);
        return expenseMapper.mapFromExpenseToExpenseResponse(expense);
    }
}
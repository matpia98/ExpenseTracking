package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class ExpenseUpdater {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    ExpenseUpdater(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }


    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with id " + id + " not found"));
        expense.setTitle(expenseRequestDto.title());
        expense.setAmount(expenseRequestDto.amount());
        expense.setDescription(expenseRequestDto.description());
        expense.setCategory(expenseRequestDto.category());
        expense.setDate(LocalDateTime.now().toString());

        return expenseMapper.mapFromExpenseToExpenseResponse(expense);

    }
}
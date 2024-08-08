package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class ExpenseUpdater {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryRepository categoryRepository;

    ExpenseUpdater(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper,
                   CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.categoryRepository = categoryRepository;
    }


    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with id " + id + " not found"));
        expense.setTitle(expenseRequestDto.title());
        expense.setAmount(expenseRequestDto.amount());
        expense.setDescription(expenseRequestDto.description());
        expense.setDate(LocalDateTime.now().toString());

        Category category = categoryRepository.findById(expenseRequestDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + expenseRequestDto.categoryId() + " not found"));
        expense.setCategory(category);

        return expenseMapper.mapFromExpenseToExpenseResponse(expense);

    }
}
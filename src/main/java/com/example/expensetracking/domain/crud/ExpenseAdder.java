package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;

import org.springframework.stereotype.Service;

@Service
class ExpenseAdder {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryRepository categoryRepository;

    ExpenseAdder(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.categoryRepository = categoryRepository;
    }

    ExpenseResponseDto addExpense(ExpenseRequestDto expenseRequestDto) {
        Category category = categoryRepository.findById(expenseRequestDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + expenseRequestDto.categoryId() + " not found"));
        Expense expenseToSave = expenseMapper.mapFromExpenseRequestToExpense(expenseRequestDto);
        expenseToSave.setCategory(category);
        category.addExpense(expenseToSave);
        Expense savedExpense = expenseRepository.save(expenseToSave);

        return expenseMapper.mapFromExpenseToExpenseResponse(savedExpense);
    }
}
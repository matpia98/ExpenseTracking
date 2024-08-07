package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.category.crud.CategoryNotFoundException;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class ExpenseMapper {
    private final CategoryInfoProvider categoryInfoProvider;

    ExpenseMapper(CategoryInfoProvider categoryInfoProvider) {
        this.categoryInfoProvider = categoryInfoProvider;
    }

    Expense mapFromExpenseRequestToExpense(ExpenseRequestDto expenseRequestDto) {
        if (!categoryInfoProvider.categoryExists(expenseRequestDto.categoryId())) {
            throw new CategoryNotFoundException("Category not found");
        }
        return Expense.builder()
                .title(expenseRequestDto.title())
                .description(expenseRequestDto.description())
                .amount(expenseRequestDto.amount())
                .categoryId(expenseRequestDto.categoryId())
                .date(LocalDateTime.now().toString())
                .build();
    }

    ExpenseResponseDto mapFromExpenseToExpenseResponse(Expense expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .categoryId(expense.getCategoryId())
                .categoryName(categoryInfoProvider.getCategoryNameById(expense.getCategoryId()))
                .build();
    }
}
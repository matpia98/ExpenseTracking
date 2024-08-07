package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class ExpenseMapper {
    Expense mapFromExpenseRequestToExpense(ExpenseRequestDto expenseRequestDto) {
        return Expense.builder()
                .title(expenseRequestDto.title())
                .description(expenseRequestDto.description())
                .amount(expenseRequestDto.amount())
                .category(expenseRequestDto.category())
                .date(LocalDateTime.now().toString())
                .categoryId(expenseRequestDto.categoryId())
                .build();
    }

    ExpenseResponseDto mapFromExpenseToExpenseResponse(Expense expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .categoryId(expense.getCategoryId())
                .build();
    }
}
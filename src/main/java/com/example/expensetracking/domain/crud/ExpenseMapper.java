package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class ExpenseMapper {

    Expense mapFromExpenseRequestToExpense(ExpenseRequestDto expenseRequestDto) {
        return Expense.builder()
                .title(expenseRequestDto.title())
                .description(expenseRequestDto.description())
                .amount(expenseRequestDto.amount())
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
                .categoryId(expense.getCategory().getId())
                .categoryName(expense.getCategory().getName())
                .build();
    }
}
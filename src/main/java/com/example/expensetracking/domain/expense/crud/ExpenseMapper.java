// src/main/java/com/example/expensetracking/domain/expense/crud/ExpenseMapper.java
package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Component;

@Component
class ExpenseMapper {
    Expense mapFromExpenseRequestToExpense(ExpenseRequestDto expenseRequestDto) {
        return Expense.builder()
                .title(expenseRequestDto.title())
                .description(expenseRequestDto.description())
                .amount(expenseRequestDto.amount())
                .build();
    }

    ExpenseResponseDto mapFromExpenseToExpenseResponse(Expense expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .build();
    }
}
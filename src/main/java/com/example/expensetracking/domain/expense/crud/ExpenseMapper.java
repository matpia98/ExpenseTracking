// src/main/java/com/example/expensetracking/domain/expense/crud/ExpenseMapper.java
package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.dto.ExpenseResponseDto;
import org.springframework.stereotype.Component;

@Component
class ExpenseMapper {
    Expense mapFromExponseRequestToExponse(ExpenseRequestDto expenseRequestDto) {
        return Expense.builder()
                .title(expenseRequestDto.title())
                .description(expenseRequestDto.description())
                .amount(expenseRequestDto.amount())
                .build();
    }

    // create a mapFromExponseToExponseResponse method that takes an Expense and returns an ExpenseResponseDto
    ExpenseResponseDto mapFromExponseToExponseResponse(Expense expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .build();
    }
}
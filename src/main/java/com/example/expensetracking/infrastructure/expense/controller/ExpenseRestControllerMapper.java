package com.example.expensetracking.infrastructure.expense.controller;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.expense.controller.dto.CreateExpenseResponseDto;
import com.example.expensetracking.infrastructure.expense.controller.dto.GetExpenseResponseDto;

class ExpenseRestControllerMapper {

    static GetExpenseResponseDto mapFromExpenseResponseDtoToGetExpenseResponseDto(ExpenseResponseDto expenseResponseDto) {
        return GetExpenseResponseDto.builder()
                .id(expenseResponseDto.id())
                .title(expenseResponseDto.title())
                .description(expenseResponseDto.description())
                .amount(expenseResponseDto.amount())
                .date(expenseResponseDto.date())
                .categoryId(expenseResponseDto.categoryId())
                .categoryName(expenseResponseDto.categoryName())
                .build();
    }

    static CreateExpenseResponseDto mapFromExpenseResponseDtoToCreateExpenseResponseDto(ExpenseResponseDto expenseResponseDto) {
        return CreateExpenseResponseDto.builder()
                .id(expenseResponseDto.id())
                .title(expenseResponseDto.title())
                .description(expenseResponseDto.description())
                .amount(expenseResponseDto.amount())
                .date(expenseResponseDto.date())
                .categoryId(expenseResponseDto.categoryId())
                .categoryName(expenseResponseDto.categoryName())
                .build();
    }
}

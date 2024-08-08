package com.example.expensetracking.infrastructure.crud.controller;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.dto.CreateExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.dto.GetExpenseResponseDto;

class ExpenseRestControllerMapper {

    static GetExpenseResponseDto mapFromExpenseResponseDtoToGetExpenseResponseDto(ExpenseResponseDto expenseResponseDto) {
        return GetExpenseResponseDto.builder()
                .id(expenseResponseDto.id())
                .title(expenseResponseDto.title())
                .description(expenseResponseDto.description())
                .amount(expenseResponseDto.amount())
                .date(expenseResponseDto.date())
                .categoryId(expenseResponseDto.categoryId())
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
                .build();
    }
}

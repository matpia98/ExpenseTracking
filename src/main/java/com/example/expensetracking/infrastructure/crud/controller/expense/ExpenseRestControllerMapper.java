package com.example.expensetracking.infrastructure.crud.controller.expense;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.CreateExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.DeleteExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetAllExpensesResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.UpdateExpenseResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

class ExpenseRestControllerMapper {

    static GetExpenseResponseDto mapFromExpenseResponseDtoToGetExpenseResponseDto(ExpenseResponseDto expenseResponseDto) {
        return GetExpenseResponseDto.builder()
                .id(expenseResponseDto.id())
                .title(expenseResponseDto.title())
                .description(expenseResponseDto.description())
                .amount(expenseResponseDto.amount())
                .date(expenseResponseDto.date().toString())
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
                .date(expenseResponseDto.date().toString())
                .categoryId(expenseResponseDto.categoryId())
                .categoryName(expenseResponseDto.categoryName())
                .build();
    }

    static GetAllExpensesResponseDto mapToGetAllExpensesResponseDto(List<ExpenseResponseDto> expenses) {
        return new GetAllExpensesResponseDto(expenses);
    }

    static UpdateExpenseResponseDto mapFromExpenseResponseDtotoUpdateExpenseResponseDto(ExpenseResponseDto expense) {
        return new UpdateExpenseResponseDto(
                expense.id(),
                expense.title(),
                expense.description(),
                expense.amount(),
                expense.date().toString(),
                expense.categoryId(),
                expense.categoryName()
        );
    }

    static DeleteExpenseResponseDto mapFromExpenseToDeleteExpenseResponseDto(Long id) {
        return new DeleteExpenseResponseDto(
                "Expense with id " + id  + " has been deleted",
                HttpStatus.OK
        );
    }
}

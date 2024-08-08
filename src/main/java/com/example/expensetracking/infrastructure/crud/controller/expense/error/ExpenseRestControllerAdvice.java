package com.example.expensetracking.infrastructure.crud.controller.expense.error;

import com.example.expensetracking.domain.crud.ExpenseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExpenseRestControllerAdvice {

    @ExceptionHandler(ExpenseNotFoundException.class)
    ResponseEntity<ExpenseErrorResponseDto> handleExpenseNotFoundException(ExpenseNotFoundException ex) {
        ExpenseErrorResponseDto expenseErrorResponseDto = new ExpenseErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(expenseErrorResponseDto);
    }

}


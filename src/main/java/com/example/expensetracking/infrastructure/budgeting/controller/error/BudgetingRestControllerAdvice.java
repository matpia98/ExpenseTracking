package com.example.expensetracking.infrastructure.budgeting.controller.error;

import com.example.expensetracking.domain.budgeting.InsufficientBudgetException;
import com.example.expensetracking.domain.budgeting.InvalidBudgetDateRangeException;
import com.example.expensetracking.domain.budgeting.NoBudgetsFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BudgetingRestControllerAdvice {

    @ExceptionHandler(NoBudgetsFoundException.class)
    ResponseEntity<BudgetErrorResponseDto> handleNoBudgetsFoundException(NoBudgetsFoundException ex) {
        BudgetErrorResponseDto budgetErrorResponseDto = new BudgetErrorResponseDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(budgetErrorResponseDto);
    }

    @ExceptionHandler(InvalidBudgetDateRangeException.class)
    ResponseEntity<BudgetErrorResponseDto> handleInvalidBudgetDateRangeException(InvalidBudgetDateRangeException ex) {
        BudgetErrorResponseDto budgetErrorResponseDto = new BudgetErrorResponseDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(budgetErrorResponseDto);
    }

    @ExceptionHandler(InsufficientBudgetException.class)
    ResponseEntity<BudgetErrorResponseDto> handleInsufficientBudgetException(InsufficientBudgetException ex) {
        BudgetErrorResponseDto budgetErrorResponseDto = new BudgetErrorResponseDto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(budgetErrorResponseDto);
    }

}


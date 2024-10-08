package com.example.expensetracking.infrastructure.crud.controller.category.error;

import com.example.expensetracking.domain.crud.CategoryHasExpensesException;
import com.example.expensetracking.domain.crud.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryRestControllerAdvice {

    @ExceptionHandler(CategoryNotFoundException.class)
    ResponseEntity<CategoryErrorResponseDto> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        CategoryErrorResponseDto categoryErrorResponseDto = new CategoryErrorResponseDto(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(categoryErrorResponseDto);
    }

    @ExceptionHandler(CategoryHasExpensesException.class)
    ResponseEntity<CategoryErrorResponseDto> handleCategoryHasExpensesException(CategoryHasExpensesException ex) {
        CategoryErrorResponseDto reportErrorResponseDto = new CategoryErrorResponseDto(
                ex.getMessage(),
                HttpStatus.CONFLICT);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(reportErrorResponseDto);
    }

}


package com.example.expensetracking.infrastructure.reporting.error;

import com.example.expensetracking.domain.reporting.InvalidDateRangeException;
import com.example.expensetracking.domain.reporting.InvalidMonthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ReportingRestControllerAdvice {

    @ExceptionHandler(InvalidDateRangeException.class)
    ResponseEntity<ReportErrorResponseDto> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        ReportErrorResponseDto reportErrorResponseDto = new ReportErrorResponseDto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(reportErrorResponseDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ReportErrorResponseDto> handleMethodArgumentTypeMismatch() {
        ReportErrorResponseDto errorResponse = new ReportErrorResponseDto(
                "Invalid date format. Please use yyyy-MM-dd",
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidMonthException.class)
    ResponseEntity<ReportErrorResponseDto> handleInvalidMonthException(InvalidMonthException ex) {
        ReportErrorResponseDto reportErrorResponseDto = new ReportErrorResponseDto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(reportErrorResponseDto);
    }

}


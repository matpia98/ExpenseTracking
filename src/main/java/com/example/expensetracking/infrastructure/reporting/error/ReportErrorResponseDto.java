package com.example.expensetracking.infrastructure.reporting.error;

import org.springframework.http.HttpStatus;

record ReportErrorResponseDto(
        String message,
        HttpStatus status
) {
}

package com.example.expensetracking.domain.reporting;

public class InvalidMonthException extends RuntimeException {
    public InvalidMonthException(String message) {
        super(message);
    }
}

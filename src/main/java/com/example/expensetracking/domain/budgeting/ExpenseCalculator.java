package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class ExpenseCalculator {
    static BigDecimal calculateSpentAmount(List<ExpenseResponseDto> expenses, LocalDate startDate, LocalDate endDate) {
        return expenses.stream()
                .filter(expense -> !expense.date().toLocalDate().isBefore(startDate) && !expense.date().toLocalDate().isAfter(endDate))
                .map(expense -> BigDecimal.valueOf(expense.amount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
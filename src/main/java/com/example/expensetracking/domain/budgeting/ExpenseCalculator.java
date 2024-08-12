package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class ExpenseCalculator {
    static BigDecimal calculateSpentAmount(List<ExpenseResponseDto> expenses, String categoryName, LocalDate startDate, LocalDate endDate) {
        return BigDecimal.valueOf(
                expenses.stream()
                        .filter(expense -> expense.categoryName().equals(categoryName))
                        .filter(expense -> !expense.date().toLocalDate().isBefore(startDate) && !expense.date().toLocalDate().isAfter(endDate))
                        .mapToDouble(ExpenseResponseDto::amount)
                        .sum()
        );
    }
}

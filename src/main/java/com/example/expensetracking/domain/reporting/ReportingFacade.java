package com.example.expensetracking.domain.reporting;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.domain.reporting.dto.ReportDto;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@AllArgsConstructor
public class ReportingFacade {

    private final ExpenseTrackingCrudFacade crudFacade;
    private final ReportGenerator reportGenerator;
    private final Clock clock;


    public ReportDto generateWeeklyReport() {
        LocalDate now = LocalDate.now(clock);
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return generateReport(startOfWeek, endOfWeek);
    }

    public ReportDto generateMonthlyReport(int year, int month) {
        validateMonth(month);
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        return generateReport(startOfMonth, endOfMonth);
    }

    public ReportDto generateReport(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }
        List<ExpenseResponseDto> allExpensesBetweenDates = crudFacade.getExpensesBetweenDates(startDate, endDate);
        return reportGenerator.generateReport(allExpensesBetweenDates, startDate, endDate);
    }

    private void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new InvalidMonthException("Month must be between 1 and 12");
        }
    }
}
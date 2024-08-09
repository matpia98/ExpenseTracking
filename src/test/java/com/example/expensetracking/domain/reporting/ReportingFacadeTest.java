package com.example.expensetracking.domain.reporting;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.domain.reporting.dto.ReportDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportingFacadeTest {
    private ExpenseTrackingCrudFacade crudFacade;
    private ReportingFacade reportingFacade;

    @BeforeEach
    void setUp() {
        crudFacade = mock(ExpenseTrackingCrudFacade.class);
        reportingFacade = new ReportingFacadeConfiguration().reportingFacade(crudFacade);
    }

    @Test
    void should_generate_weekly_report() {
        // given
        LocalDate date = LocalDate.of(2024, 8, 15);  // A Tuesday
        LocalDate startOfWeek = LocalDate.of(2024, 8, 12);  // Monday
        LocalDate endOfWeek = LocalDate.of(2024, 8, 18);  // Sunday
        List<ExpenseResponseDto> mockExpenses = Arrays.asList(
                new ExpenseResponseDto(1L, "Expense 1", "Description 1", 100.0, LocalDateTime.of(2024, 8, 15, 10, 0), 1L, "Category 1"),
                new ExpenseResponseDto(2L, "Expense 2", "Description 2", 200.0, LocalDateTime.of(2024, 8, 16, 11, 0), 2L, "Category 2")
        );
        when(crudFacade.getExpensesBetweenDates(startOfWeek, endOfWeek)).thenReturn(mockExpenses);

        // when
        ReportDto report = reportingFacade.generateWeeklyReport(date);

        // then
        assertThat(report.startDate()).isEqualTo(startOfWeek.atStartOfDay());
        assertThat(report.endDate()).isEqualTo(endOfWeek.plusDays(1).atStartOfDay().minusNanos(1));
        assertThat(report.totalExpenses()).isEqualByComparingTo(BigDecimal.valueOf(300.0));
        assertThat(report.categorySummaries()).hasSize(2);
        assertThat(report.topExpenses()).hasSize(2);
    }

    @Test
    void should_generate_monthly_report() {
        // given
        int year = 2024;
        int month = 8;
        LocalDate startOfMonth = LocalDate.of(2024, 8, 1);
        LocalDate endOfMonth = LocalDate.of(2024, 8, 31);
        List<ExpenseResponseDto> mockExpenses = Arrays.asList(
                new ExpenseResponseDto(1L, "Expense 1", "Description 1", 100.0, LocalDateTime.of(2024, 8, 15, 10, 0), 1L, "Category 1"),
                new ExpenseResponseDto(2L, "Expense 2", "Description 2", 200.0, LocalDateTime.of(2024, 8, 16, 11, 0), 2L, "Category 2"),
                new ExpenseResponseDto(3L, "Expense 3", "Description 3", 300.0, LocalDateTime.of(2024, 8, 17, 12, 0), 1L, "Category 1")
        );
        when(crudFacade.getExpensesBetweenDates(startOfMonth, endOfMonth)).thenReturn(mockExpenses);

        // when
        ReportDto report = reportingFacade.generateMonthlyReport(year, month);

        // then
        assertThat(report.startDate()).isEqualTo(startOfMonth.atStartOfDay());
        assertThat(report.endDate()).isEqualTo(endOfMonth.plusDays(1).atStartOfDay().minusNanos(1));
        assertThat(report.totalExpenses()).isEqualByComparingTo(BigDecimal.valueOf(600.0));
        assertThat(report.categorySummaries()).hasSize(2);
        assertThat(report.topExpenses()).hasSize(3);
    }

    @Test
    void should_generate_report_for_custom_date_range() {
        // given
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 15);
        List<ExpenseResponseDto> mockExpenses = Arrays.asList(
                new ExpenseResponseDto(1L, "Expense 1", "Description 1", 100.0, LocalDateTime.of(2024, 8, 5, 10, 0), 1L, "Category 1"),
                new ExpenseResponseDto(2L, "Expense 2", "Description 2", 200.0, LocalDateTime.of(2024, 8, 10, 11, 0),2L,"Category 2")
        );
        when(crudFacade.getExpensesBetweenDates(startDate, endDate)).thenReturn(mockExpenses);

        // when
        ReportDto report = reportingFacade.generateReport(startDate, endDate);

        // then
        assertThat(report).isNotNull();
        assertThat(report.startDate()).isEqualTo(startDate.atStartOfDay());
        assertThat(report.endDate()).isEqualTo(endDate.plusDays(1).atStartOfDay().minusNanos(1));
        assertThat(report.totalExpenses()).isEqualByComparingTo(BigDecimal.valueOf(300.0));
        assertThat(report.categorySummaries()).hasSize(2);
        assertThat(report.topExpenses()).hasSize(2);
    }

    @Test
    void should_throw_exception_when_start_date_is_after_end_date() {
        // given
        LocalDate startDate = LocalDate.of(2024, 8, 15);
        LocalDate endDate = LocalDate.of(2024, 8, 1);

        // when & then
        assertThatThrownBy(() -> reportingFacade.generateReport(startDate, endDate))
                .isInstanceOf(InvalidDateRangeException.class)
                .hasMessage("Start date cannot be after end date");
    }

    @Test
    void should_generate_empty_report_when_no_expenses() {
        // given
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 15);
        when(crudFacade.getExpensesBetweenDates(startDate, endDate)).thenReturn(Collections.emptyList());

        // when
        ReportDto report = reportingFacade.generateReport(startDate, endDate);

        // then
        assertThat(report).isNotNull();
        assertThat(report.totalExpenses()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(report.categorySummaries()).isEmpty();
        assertThat(report.topExpenses()).isEmpty();
    }


}
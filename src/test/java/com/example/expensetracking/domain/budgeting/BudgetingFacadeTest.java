package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.dto.AddExpenseToBudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetRequestDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;
import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BudgetingFacadeTest {

    private BudgetingFacade budgetingFacade;
    private ExpenseTrackingCrudFacade expenseTrackingCrudFacade;
    private InMemoryBudgetRepository budgetRepository;

    @BeforeEach
    void setUp() {
        expenseTrackingCrudFacade = mock(ExpenseTrackingCrudFacade.class);
        budgetRepository = new InMemoryBudgetRepository();

        // Create a fixed clock for testing
        Clock fixedClock = Clock.fixed(Instant.parse("2024-01-15T10:00:00Z"), ZoneId.systemDefault());

        budgetingFacade = new BudgetingFacadeConfiguration().budgetingFacade(
                expenseTrackingCrudFacade,
                budgetRepository,
                fixedClock
        );
    }

    @Test
    void should_create_budget() {
        // given
        final LocalDate fixedDate = LocalDate.of(2024, 1, 1);
        BudgetRequestDto requestDto = new BudgetRequestDto(fixedDate, fixedDate.plusDays(30), BigDecimal.valueOf(1000));

        // when
        BudgetResponseDto result = budgetingFacade.createBudget(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isPositive();
        assertThat(result.startDate()).isEqualTo(requestDto.startDate());
        assertThat(result.endDate()).isEqualTo(requestDto.endDate());
        assertThat(result.spent()).isEqualTo(BigDecimal.ZERO);
        assertThat(result.remaining()).isEqualTo(requestDto.initialAmount());
        assertThat(result.expenses()).isEmpty();
    }

    @Test
    void should_throw_exception_when_creating_budget_with_invalid_date_range() {
        // given
        final LocalDate fixedDate = LocalDate.of(2024, 1, 1);
        BudgetRequestDto requestDto = new BudgetRequestDto(fixedDate.plusDays(1), fixedDate, BigDecimal.valueOf(1000));

        // when & then
        assertThatThrownBy(() -> budgetingFacade.createBudget(requestDto))
                .isInstanceOf(InvalidBudgetDateRangeException.class)
                .hasMessage("End date must be after start date");
    }

    @Test
    void should_add_existing_expense_to_budget() {
        // given
        final LocalDate fixedDate = LocalDate.of(2024, 1, 1);
        BudgetRequestDto budgetRequestDto = new BudgetRequestDto(fixedDate, fixedDate.plusDays(30), BigDecimal.valueOf(1000));
        BudgetResponseDto createdBudget = budgetingFacade.createBudget(budgetRequestDto);

        ExpenseResponseDto expenseDto = new ExpenseResponseDto(1L, "Test Expense", "Description", 100.0, fixedDate.atStartOfDay(), 1L, "Category", null);
        when(expenseTrackingCrudFacade.getExpenseById(1L)).thenReturn(expenseDto);

        // when
        AddExpenseToBudgetResponseDto result = budgetingFacade.addExistingExpenseToBudget(createdBudget.id(), 1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.budgetId()).isEqualTo(createdBudget.id());
        assertThat(result.expenseId()).isEqualTo(1L);
        assertThat(result.updatedSpent()).isEqualTo(BigDecimal.valueOf(100.0));
        assertThat(result.updatedRemaining()).isEqualTo(BigDecimal.valueOf(900.0));
        assertThat(result.addedExpense().title()).isEqualTo("Test Expense");
        assertThat(result.addedExpense().amount()).isEqualTo(BigDecimal.valueOf(100.0));

        verify(expenseTrackingCrudFacade).getExpenseById(1L);
    }

    @Test
    void should_summarize_active_budgets() {
        // given
        final LocalDate fixedDate = LocalDate.of(2024, 1, 1);
        BudgetRequestDto budget1 = new BudgetRequestDto(fixedDate, fixedDate.plusDays(30), BigDecimal.valueOf(1000));
        BudgetRequestDto budget2 = new BudgetRequestDto(fixedDate.plusDays(1), fixedDate.plusDays(31), BigDecimal.valueOf(1500));

        BudgetResponseDto createdBudget1 = budgetingFacade.createBudget(budget1);
        BudgetResponseDto createdBudget2 = budgetingFacade.createBudget(budget2);

        // when
        List<BudgetSummaryDto> result = budgetingFacade.summarizeActiveBudgets();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).startDate()).isEqualTo(createdBudget1.startDate());
        assertThat(result.get(0).endDate()).isEqualTo(createdBudget1.endDate());
        assertThat(result.get(1).startDate()).isEqualTo(createdBudget2.startDate());
        assertThat(result.get(1).endDate()).isEqualTo(createdBudget2.endDate());
    }

    @Test
    void should_get_budget_by_id() {
        // given
        final LocalDate fixedDate = LocalDate.of(2024, 1, 1);
        BudgetRequestDto requestDto = new BudgetRequestDto(fixedDate, fixedDate.plusDays(30), BigDecimal.valueOf(1000));
        BudgetResponseDto createdBudget = budgetingFacade.createBudget(requestDto);

        // when
        BudgetResponseDto result = budgetingFacade.getBudgetById(createdBudget.id());

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(createdBudget.id());
        assertThat(result.startDate()).isEqualTo(requestDto.startDate());
        assertThat(result.endDate()).isEqualTo(requestDto.endDate());
        assertThat(result.spent()).isEqualTo(BigDecimal.ZERO);
        assertThat(result.remaining()).isEqualTo(requestDto.initialAmount());
        assertThat(result.expenses()).isEmpty();
    }
}
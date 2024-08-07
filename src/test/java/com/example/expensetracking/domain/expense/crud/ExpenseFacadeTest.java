package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseFacadeTest {

    private ExpenseFacade expenseFacade;
    private InMemoryExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository = new InMemoryExpenseRepository();
        expenseFacade = new ExpenseFacadeConfiguration().expenseFacade(expenseRepository);
    }

    @Test
    void should_add_expense() {
        // given
        ExpenseRequestDto requestDto = new ExpenseRequestDto("Test Expense", "Description", 100.0, "Food", 1L);

        // when
        ExpenseResponseDto result = expenseFacade.addExpense(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isPositive();
        assertThat(result.title()).isEqualTo("Test Expense");
        assertThat(result.amount()).isEqualTo(100.0);
        assertThat(result.category()).isEqualTo("Food");
        assertThat(result.categoryId()).isEqualTo(1L);
    }

    @Test
    void should_get_all_expenses() {
        // given
        expenseFacade.addExpense(new ExpenseRequestDto("Expense 1", "Description 1", 100.0, "Food", 1L));
        expenseFacade.addExpense(new ExpenseRequestDto("Expense 2", "Description 2", 200.0, "Transport", 2L));

        // when
        List<ExpenseResponseDto> result = expenseFacade.getAllExpenses();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ExpenseResponseDto::title).containsExactly("Expense 1", "Expense 2");
    }

    @Test
    void should_get_expense_by_id() {
        // given
        ExpenseResponseDto addedExpense = expenseFacade.addExpense(new ExpenseRequestDto("Test Expense", "Description", 100.0, "Food", 1L));

        // when
        ExpenseResponseDto result = expenseFacade.getExpenseById(addedExpense.id());

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(addedExpense.id());
        assertThat(result.title()).isEqualTo("Test Expense");
    }

    @Test
    void should_update_expense() {
        // given
        ExpenseResponseDto addedExpense = expenseFacade.addExpense(new ExpenseRequestDto("Test Expense", "Description", 100.0, "Food", 1L));
        ExpenseRequestDto updateDto = new ExpenseRequestDto("Updated Expense", "New Description", 150.0, "Food", 1L);

        // when
        ExpenseResponseDto result = expenseFacade.updateExpense(addedExpense.id(), updateDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(addedExpense.id());
        assertThat(result.title()).isEqualTo("Updated Expense");
        assertThat(result.amount()).isEqualTo(150.0);
    }

    @Test
    void should_delete_expense() {
        // given
        ExpenseResponseDto addedExpense = expenseFacade.addExpense(new ExpenseRequestDto("Test Expense", "Description", 100.0, "Food", 1L));

        // when
        expenseFacade.deleteExpense(addedExpense.id());

        // then
        assertThrows(ExpenseNotFoundException.class, () -> expenseFacade.getExpenseById(addedExpense.id()));
    }

    @Test
    void should_throw_exception_when_deleting_non_existent_expense() {
        // given
        Long nonExistentId = 999L;

        // when & then
        assertThrows(ExpenseNotFoundException.class, () -> expenseFacade.deleteExpense(nonExistentId));
    }

    @Test
    void should_get_expenses_by_categoryId() {
        // given
        Long categoryId = 1L;
        expenseFacade.addExpense(new ExpenseRequestDto("Expense 1", "Description 1", 100.0, "Food", categoryId));
        expenseFacade.addExpense(new ExpenseRequestDto("Expense 2", "Description 2", 200.0, "Food", categoryId));
        expenseFacade.addExpense(new ExpenseRequestDto("Expense 3", "Description 3", 300.0, "Transport", 2L));

        // when
        List<ExpenseResponseDto> result = expenseFacade.getExpensesByCategoryId(categoryId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ExpenseResponseDto::categoryId).containsOnly(categoryId);
    }
}
package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;
import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseTrackingCrudFacadeTest {

    private ExpenseTrackingCrudFacade expenseTrackingCrudFacade;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = new InMemoryCategoryRepository();
        ExpenseRepository expenseRepository = new InMemoryExpenseRepository(categoryRepository);
        expenseTrackingCrudFacade = new ExpenseFacadeConfiguration().expenseFacade(expenseRepository, categoryRepository);

        // Add some initial categories
        categoryRepository.save(new Category("Food"));
        categoryRepository.save(new Category("Transport"));
    }

    @Test
    void should_add_expense() {
        // given
        ExpenseRequestDto requestDto = new ExpenseRequestDto("Test Expense", "Description", 100.0, 1L);

        // when
        ExpenseResponseDto result = expenseTrackingCrudFacade.addExpense(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isPositive();
        assertThat(result.title()).isEqualTo("Test Expense");
        assertThat(result.amount()).isEqualTo(100.0);
        assertThat(result.categoryId()).isEqualTo(1L);
    }

    @Test
    void should_get_all_expenses() {
        // given
        expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Expense 1", "Description 1", 100.0, 1L));
        expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Expense 2", "Description 2", 200.0, 2L));

        // when
        List<ExpenseResponseDto> result = expenseTrackingCrudFacade.getAllExpenses();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ExpenseResponseDto::title).containsExactly("Expense 1", "Expense 2");
    }

    @Test
    void should_get_expense_by_id() {
        // given
        ExpenseResponseDto addedExpense = expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Test Expense", "Description", 100.0, 1L));

        // when
        ExpenseResponseDto result = expenseTrackingCrudFacade.getExpenseById(addedExpense.id());

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(addedExpense.id());
        assertThat(result.title()).isEqualTo("Test Expense");
    }

    @Test
    void should_update_expense() {
        // given
        ExpenseResponseDto addedExpense = expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Test Expense", "Description", 100.0, 1L));
        ExpenseRequestDto updateDto = new ExpenseRequestDto("Updated Expense", "New Description", 150.0, 2L);

        // when
        ExpenseResponseDto result = expenseTrackingCrudFacade.updateExpense(addedExpense.id(), updateDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(addedExpense.id());
        assertThat(result.title()).isEqualTo("Updated Expense");
        assertThat(result.amount()).isEqualTo(150.0);
        assertThat(result.categoryId()).isEqualTo(2L);
    }

    @Test
    void should_delete_expense() {
        // given
        ExpenseResponseDto addedExpense = expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Test Expense", "Description", 100.0, 1L));

        // when
        expenseTrackingCrudFacade.deleteExpense(addedExpense.id());

        // then
        assertThrows(ExpenseNotFoundException.class, () -> expenseTrackingCrudFacade.getExpenseById(addedExpense.id()));
    }

    @Test
    void should_get_expenses_by_categoryId() {
        // given
        Long categoryId = 1L;
        expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Expense 1", "Description 1", 100.0, categoryId));
        expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Expense 2", "Description 2", 200.0, categoryId));
        expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Expense 3", "Description 3", 300.0, 2L));

        // when
        List<ExpenseResponseDto> result = expenseTrackingCrudFacade.getExpensesByCategoryId(categoryId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ExpenseResponseDto::categoryId).containsOnly(categoryId);
    }

    @Test
    void should_add_category() {
        // given
        CategoryRequestDto requestDto = new CategoryRequestDto("Entertainment");

        // when
        CategoryResponseDto result = expenseTrackingCrudFacade.addCategory(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isPositive();
        assertThat(result.name()).isEqualTo("Entertainment");
    }

    @Test
    void should_get_all_categories() {
        // given
        expenseTrackingCrudFacade.addCategory(new CategoryRequestDto("Entertainment"));

        // when
        List<CategoryResponseDto> result = expenseTrackingCrudFacade.getAllCategories();

        // then
        assertThat(result).hasSize(3); // 2 initial categories + 1 new
        assertThat(result).extracting(CategoryResponseDto::name).contains("Food", "Transport", "Entertainment");
    }

    @Test
    void should_get_category_by_id() {
        // given
        CategoryResponseDto addedCategory = expenseTrackingCrudFacade.addCategory(new CategoryRequestDto("Entertainment"));

        // when
        CategoryResponseDto result = expenseTrackingCrudFacade.getCategoryById(addedCategory.id());

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(addedCategory.id());
        assertThat(result.name()).isEqualTo("Entertainment");
    }

    @Test
    void should_update_category() {
        // given
        CategoryResponseDto addedCategory = expenseTrackingCrudFacade.addCategory(new CategoryRequestDto("Entertainment"));
        CategoryRequestDto updateDto = new CategoryRequestDto("Leisure");

        // when
        CategoryResponseDto result = expenseTrackingCrudFacade.updateCategory(addedCategory.id(), updateDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(addedCategory.id());
        assertThat(result.name()).isEqualTo("Leisure");
    }

    @Test
    void should_delete_category() {
        // given
        CategoryResponseDto addedCategory = expenseTrackingCrudFacade.addCategory(new CategoryRequestDto("Entertainment"));

        // when
        expenseTrackingCrudFacade.deleteCategory(addedCategory.id());

        // then
        assertThatThrownBy(() -> expenseTrackingCrudFacade.getCategoryById(addedCategory.id()))
                .isInstanceOf(CategoryNotFoundException.class);
    }


    @Test
    void should_throw_exception_when_deleting_category_with_expenses() {
        // given
        CategoryResponseDto addedCategory = expenseTrackingCrudFacade.addCategory(new CategoryRequestDto("Entertainment"));
        expenseTrackingCrudFacade.addExpense(new ExpenseRequestDto("Test Expense", "Description", 100.0, addedCategory.id()));

        // when & then
        assertThatThrownBy(() -> expenseTrackingCrudFacade.deleteCategory(addedCategory.id()))
                .isInstanceOf(CategoryHasExpensesException.class)
                .hasMessageContaining("has expenses");
    }

    @Test
    void should_throw_exception_when_adding_expense_with_non_existent_category_id() {
        // given
        Long nonExistentCategoryId = 999L;
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto("Test Expense", "Description", 100.0, nonExistentCategoryId);

        // when & then
        assertThatThrownBy(() -> expenseTrackingCrudFacade.addExpense(expenseRequestDto))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category with id 999 not found");
    }
}
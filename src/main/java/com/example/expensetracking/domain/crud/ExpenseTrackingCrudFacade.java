package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;
import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ExpenseTrackingCrudFacade {

    private final ExpenseAdder expenseAdder;
    private final ExpenseRetriever expenseRetriever;
    private final ExpenseDeleter expenseDeleter;
    private final ExpenseUpdater expenseUpdater;
    private final CategoryAdder categoryAdder;
    private final CategoryRetriever categoryRetriever;
    private final CategoryDeleter categoryDeleter;
    private final CategoryUpdater categoryUpdater;

    @Transactional
    public ExpenseResponseDto addExpense(ExpenseRequestDto expenseRequestDto) {
        return expenseAdder.addExpense(expenseRequestDto);
    }

    public List<ExpenseResponseDto> getAllExpenses() {
        return expenseRetriever.getAllExpenses();
    }

    public ExpenseResponseDto getExpenseById(Long id) {
        return expenseRetriever.getExpenseById(id);
    }

    @Transactional
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        return expenseUpdater.updateExpense(id, expenseRequestDto);
    }

    public void deleteExpense(Long id) {
        expenseDeleter.deleteById(id);
    }

    public List<ExpenseResponseDto> getExpensesByCategoryId(Long categoryId) {
        return expenseRetriever.getExpensesByCategoryId(categoryId);
    }

    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        return categoryAdder.addCategory(categoryRequestDto);
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRetriever.getCategoriesById();
    }

    public CategoryResponseDto getCategoryById(Long id) {
        return categoryRetriever.getCategoryById(id);
    }

    public void deleteCategory(Long id) {
        categoryDeleter.deleteById(id);
    }

    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto){
        return categoryUpdater.updateCategory(id, categoryRequestDto);
    }
}
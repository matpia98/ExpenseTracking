package com.example.expensetracking.domain.category.crud;

import com.example.expensetracking.domain.category.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.category.crud.dto.CategoryResponseDto;
import com.example.expensetracking.domain.expense.crud.ExpenseFacade;
import com.example.expensetracking.domain.expense.crud.ExpenseNotFoundException;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryFacade {

    private final CategoryAdder categoryAdder;
    private final CategoryRetriever categoryRetriever;
    private final CategoryDeleter categoryDeleter;
    private final CategoryUpdater categoryUpdater;
    private final ExpenseFacade expenseFacade;


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
        try {
            List<ExpenseResponseDto> expensesByCategoryId = expenseFacade.getExpensesByCategoryId(id);
            throw new CategoryHasExpensesException("Category with id " + id + " has expenses");
        } catch (ExpenseNotFoundException e) {
            categoryDeleter.deleteById(id);
        }

    }

        @Transactional
        public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto){
            return categoryUpdater.updateCategory(id, categoryRequestDto);
        }


    }

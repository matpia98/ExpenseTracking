package com.example.expensetracking.domain.crud;

import org.springframework.stereotype.Service;

@Service
class CategoryDeleter {

    private final CategoryRepository categoryRepository;

    CategoryDeleter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));

        if (category.hasExpenses()) {
            throw new CategoryHasExpensesException("Category with id " + id + " has expenses");
        }

        categoryRepository.deleteById(id);
    }
}
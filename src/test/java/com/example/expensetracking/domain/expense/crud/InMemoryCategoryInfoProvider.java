package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.category.crud.CategoryNotFoundException;

import java.util.HashMap;
import java.util.Map;

class InMemoryCategoryInfoProvider implements CategoryInfoProvider {
    private final Map<Long, String> categories = new HashMap<>();

    void addCategory(Long id, String name) {
        categories.put(id, name);
    }

    @Override
    public String getCategoryNameById(Long categoryId) {
        String categoryName = categories.get(categoryId);
        if (categoryName == null) {
            throw new CategoryNotFoundException("Category not found");
        }
        return categoryName;
    }

    @Override
    public boolean categoryExists(Long categoryId) {
        return categories.containsKey(categoryId);
    }
}
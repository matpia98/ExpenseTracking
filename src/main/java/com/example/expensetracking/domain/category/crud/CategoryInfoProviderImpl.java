package com.example.expensetracking.domain.category.crud;

import com.example.expensetracking.domain.expense.crud.CategoryInfoProvider;
import org.springframework.stereotype.Service;

@Service
class CategoryInfoProviderImpl implements CategoryInfoProvider {
    private final CategoryRepository categoryRepository;

    public CategoryInfoProviderImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String getCategoryNameById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(Category::getName)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public boolean categoryExists(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
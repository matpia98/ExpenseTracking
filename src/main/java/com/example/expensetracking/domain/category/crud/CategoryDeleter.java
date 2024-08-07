package com.example.expensetracking.domain.category.crud;

import org.springframework.stereotype.Service;

@Service
class CategoryDeleter {

    private final CategoryRepository categoryRepository;

    CategoryDeleter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
}

package com.example.expensetracking.domain.category.crud;

import com.example.expensetracking.domain.category.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.category.crud.dto.CategoryResponseDto;
import org.springframework.stereotype.Service;

@Service
class CategoryAdder {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    CategoryAdder(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.mapFromCategoryRequestToCategory(categoryRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapFromCategoryToCategoryResponse(savedCategory);
    }
}

package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;
import org.springframework.stereotype.Service;

@Service
class CategoryUpdater {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    CategoryUpdater(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
        category.setName(requestDto.name());
        return categoryMapper.mapFromCategoryToCategoryResponse(category);
    }
}

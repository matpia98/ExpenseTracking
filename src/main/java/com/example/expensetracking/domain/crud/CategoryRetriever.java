package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class CategoryRetriever {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    CategoryRetriever(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    List<CategoryResponseDto> getCategoriesById() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::mapFromCategoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
        return categoryMapper.mapFromCategoryToCategoryResponse(category);
    }
}

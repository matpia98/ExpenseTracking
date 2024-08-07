package com.example.expensetracking.domain.category.crud;

import com.example.expensetracking.domain.category.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.category.crud.dto.CategoryResponseDto;
import org.springframework.stereotype.Service;

@Service
class CategoryMapper {


    CategoryResponseDto mapFromCategoryToCategoryResponse(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    Category mapFromCategoryRequestToCategory(CategoryRequestDto categoryRequestDto) {
        return Category.builder()
                .name(categoryRequestDto.name())
                .build();
    }
}

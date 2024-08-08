package com.example.expensetracking.infrastructure.crud.controller.category.dto;

import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;

import java.util.List;

public record GetAllCategoriesResponseDto(List<CategoryResponseDto> categories) {
}

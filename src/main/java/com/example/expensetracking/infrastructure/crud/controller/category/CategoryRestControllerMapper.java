package com.example.expensetracking.infrastructure.crud.controller.category;

import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;

import com.example.expensetracking.infrastructure.crud.controller.category.dto.CreateCategoryResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.DeleteCategoryResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.GetAllCategoriesResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.GetCategoryResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.GetExpensesByCategoryResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.UpdateCategoryResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

class CategoryRestControllerMapper {

    static CreateCategoryResponseDto mapFromCategoryResponseDtoToCreateCategoryResponseDto(CategoryResponseDto categoryResponseDto) {
        return CreateCategoryResponseDto.builder()
                .id(categoryResponseDto.id())
                .name(categoryResponseDto.name())
                .build();
    }

    static GetCategoryResponseDto mapFromCategoryResponseDtoToGetCategoryResponseDto(CategoryResponseDto categoryResponseDto) {
        return GetCategoryResponseDto.builder()
                .id(categoryResponseDto.id())
                .name(categoryResponseDto.name())
                .build();
    }

    static GetAllCategoriesResponseDto mapToGetAllCategoriesResponseDto(List<CategoryResponseDto> categories) {
        return new GetAllCategoriesResponseDto(categories);
    }

    static UpdateCategoryResponseDto mapFromCategoryResponseDtoToUpdateCategoryResponseDto(CategoryResponseDto category) {
        return new UpdateCategoryResponseDto(
                category.id(),
                category.name()
        );
    }

    static DeleteCategoryResponseDto mapFromCategoryToDeleteCategoryResponseDto(Long id) {
        return new DeleteCategoryResponseDto(
                "Category with id " + id + " has been deleted",
                HttpStatus.OK
        );
    }

    static GetExpensesByCategoryResponseDto mapToGetExpensesByCategoryResponseDto(List<ExpenseResponseDto> expenses) {
        return new GetExpensesByCategoryResponseDto(expenses);
    }
}
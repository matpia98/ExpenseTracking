package com.example.expensetracking.domain.category.crud;

import com.example.expensetracking.domain.category.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.category.crud.dto.CategoryResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryFacade {

    private final CategoryAdder categoryAdder;
    private final CategoryRetriever categoryRetriever;
    private final CategoryDeleter categoryDeleter;
    private final CategoryUpdater categoryUpdater;


    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        return categoryAdder.addCategory(categoryRequestDto);
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRetriever.getCategoriesById();
    }

    public CategoryResponseDto getCategoryById(Long id) {
        return categoryRetriever.getCategoryById(id);
    }

    public void deleteCategory(Long id) {
        categoryDeleter.deleteById(id);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        return categoryUpdater.updateCategory(id, categoryRequestDto);
    }



}

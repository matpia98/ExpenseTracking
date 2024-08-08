package com.example.expensetracking.infrastructure.crud.controller.category;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.expensetracking.infrastructure.crud.controller.category.CategoryRestControllerMapper.*;

@RestController
@RequestMapping("/categories")
class CategoryRestController {

    private final ExpenseTrackingCrudFacade expenseTrackingCrudFacade;

    CategoryRestController(ExpenseTrackingCrudFacade expenseTrackingCrudFacade) {
        this.expenseTrackingCrudFacade = expenseTrackingCrudFacade;
    }

    @PostMapping
    ResponseEntity<CreateCategoryResponseDto> addCategory(@Valid @RequestBody CategoryRequestDto request) {
        CategoryResponseDto categoryResponseDto = expenseTrackingCrudFacade.addCategory(request);
        CreateCategoryResponseDto createCategoryResponseDto = mapFromCategoryResponseDtoToCreateCategoryResponseDto(categoryResponseDto);
        return ResponseEntity.ok(createCategoryResponseDto);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<GetCategoryResponseDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = expenseTrackingCrudFacade.getCategoryById(categoryId);
        GetCategoryResponseDto getCategoryResponseDto = mapFromCategoryResponseDtoToGetCategoryResponseDto(categoryResponseDto);
        return ResponseEntity.ok(getCategoryResponseDto);
    }

    @GetMapping
    ResponseEntity<GetAllCategoriesResponseDto> getAllCategories() {
        List<CategoryResponseDto> categories = expenseTrackingCrudFacade.getAllCategories();
        GetAllCategoriesResponseDto response = mapToGetAllCategoriesResponseDto(categories);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<UpdateCategoryResponseDto> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestDto request) {
        CategoryResponseDto updatedCategory = expenseTrackingCrudFacade.updateCategory(categoryId, request);
        UpdateCategoryResponseDto response = mapFromCategoryResponseDtoToUpdateCategoryResponseDto(updatedCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<DeleteCategoryResponseDto> deleteCategory(@PathVariable Long categoryId) {
        expenseTrackingCrudFacade.deleteCategory(categoryId);
        DeleteCategoryResponseDto response = mapFromCategoryToDeleteCategoryResponseDto(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}/expenses")
    ResponseEntity<GetExpensesByCategoryResponseDto> getExpensesByCategoryId(@PathVariable Long categoryId) {
        List<ExpenseResponseDto> expenses = expenseTrackingCrudFacade.getExpensesByCategoryId(categoryId);
        GetExpensesByCategoryResponseDto response = mapToGetExpensesByCategoryResponseDto(expenses);
        return ResponseEntity.ok(response);
    }
}







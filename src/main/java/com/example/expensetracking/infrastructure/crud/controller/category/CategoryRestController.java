package com.example.expensetracking.infrastructure.crud.controller.category;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.CategoryRequestDto;
import com.example.expensetracking.domain.crud.dto.CategoryResponseDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.expensetracking.infrastructure.crud.controller.category.CategoryRestControllerMapper.*;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories")
class CategoryRestController {

    private final ExpenseTrackingCrudFacade expenseTrackingCrudFacade;

    CategoryRestController(ExpenseTrackingCrudFacade expenseTrackingCrudFacade) {
        this.expenseTrackingCrudFacade = expenseTrackingCrudFacade;
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates a new expense category")
    @ApiResponse(responseCode = "200", description = "Successfully created the category")
    ResponseEntity<CreateCategoryResponseDto> addCategory(@Valid @RequestBody CategoryRequestDto request) {
        CategoryResponseDto categoryResponseDto = expenseTrackingCrudFacade.addCategory(request);
        CreateCategoryResponseDto createCategoryResponseDto = mapFromCategoryResponseDtoToCreateCategoryResponseDto(categoryResponseDto);
        return ResponseEntity.ok(createCategoryResponseDto);
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get a category by ID", description = "Retrieves a category by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the category")
    @ApiResponse(responseCode = "404", description = "Category not found")
    ResponseEntity<GetCategoryResponseDto> getCategoryById(@Parameter(description = "ID of the category to retrieve") @PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = expenseTrackingCrudFacade.getCategoryById(categoryId);
        GetCategoryResponseDto getCategoryResponseDto = mapFromCategoryResponseDtoToGetCategoryResponseDto(categoryResponseDto);
        return ResponseEntity.ok(getCategoryResponseDto);
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves a list of all expense categories")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
    ResponseEntity<GetAllCategoriesResponseDto> getAllCategories() {
        List<CategoryResponseDto> categories = expenseTrackingCrudFacade.getAllCategories();
        GetAllCategoriesResponseDto response = mapToGetAllCategoriesResponseDto(categories);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Update a category", description = "Updates an existing category")
    @ApiResponse(responseCode = "200", description = "Successfully updated the category")
    @ApiResponse(responseCode = "404", description = "Category not found")
    ResponseEntity<UpdateCategoryResponseDto> updateCategory(@Parameter(description = "ID of the category to update") @PathVariable Long categoryId,
                                                             @Valid @RequestBody CategoryRequestDto request) {
        CategoryResponseDto updatedCategory = expenseTrackingCrudFacade.updateCategory(categoryId, request);
        UpdateCategoryResponseDto response = mapFromCategoryResponseDtoToUpdateCategoryResponseDto(updatedCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete a category", description = "Deletes an existing category")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the category")
    @ApiResponse(responseCode = "404", description = "Category not found")
    ResponseEntity<DeleteCategoryResponseDto> deleteCategory(@Parameter(description = "ID of the category to delete") @PathVariable Long categoryId) {
        expenseTrackingCrudFacade.deleteCategory(categoryId);
        DeleteCategoryResponseDto response = mapFromCategoryToDeleteCategoryResponseDto(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}/expenses")
    @Operation(summary = "Get expenses by category", description = "Retrieves all expenses for a specific category")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved expenses for the category")
    @ApiResponse(responseCode = "404", description = "Category not found")
    ResponseEntity<GetExpensesByCategoryResponseDto> getExpensesByCategoryId(@Parameter(description = "ID of the category to get expenses for") @PathVariable Long categoryId) {
        List<ExpenseResponseDto> expenses = expenseTrackingCrudFacade.getExpensesByCategoryId(categoryId);
        GetExpensesByCategoryResponseDto response = mapToGetExpensesByCategoryResponseDto(expenses);
        return ResponseEntity.ok(response);
    }
}







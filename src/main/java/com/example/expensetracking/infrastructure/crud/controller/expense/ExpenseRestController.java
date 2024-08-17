package com.example.expensetracking.infrastructure.crud.controller.expense;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.CreateExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.DeleteExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetAllExpensesResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.UpdateExpenseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.expensetracking.infrastructure.crud.controller.expense.ExpenseRestControllerMapper.mapFromExpenseResponseDtoToCreateExpenseResponseDto;
import static com.example.expensetracking.infrastructure.crud.controller.expense.ExpenseRestControllerMapper.mapFromExpenseResponseDtoToGetExpenseResponseDto;
import static com.example.expensetracking.infrastructure.crud.controller.expense.ExpenseRestControllerMapper.mapFromExpenseToDeleteExpenseResponseDto;
import static com.example.expensetracking.infrastructure.crud.controller.expense.ExpenseRestControllerMapper.mapToGetAllExpensesResponseDto;

@RestController
@RequestMapping("/expenses")
@Tag(name = "Expenses", description = "Endpoints for managing expenses")
class ExpenseRestController {

    private final ExpenseTrackingCrudFacade expenseTrackingCrudFacade;

    ExpenseRestController(ExpenseTrackingCrudFacade expenseTrackingCrudFacade) {
        this.expenseTrackingCrudFacade = expenseTrackingCrudFacade;
    }

    @GetMapping("/{expenseId}")
    @Operation(summary = "Get an expense by ID", description = "Retrieves an expense by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the expense")
    @ApiResponse(responseCode = "404", description = "Expense not found")
    ResponseEntity<GetExpenseResponseDto> getExpenseById(@Parameter(description = "ID of the expense to retrieve") @PathVariable Long expenseId) {
        ExpenseResponseDto expenseResponseDto = expenseTrackingCrudFacade.getExpenseById(expenseId);
        GetExpenseResponseDto getExpenseResponseDto = mapFromExpenseResponseDtoToGetExpenseResponseDto(expenseResponseDto);
        return ResponseEntity.ok(getExpenseResponseDto);
    }

    @PostMapping()
    @Operation(summary = "Create a new expense", description = "Creates a new expense")
    @ApiResponse(responseCode = "200", description = "Successfully created the expense")
    ResponseEntity<CreateExpenseResponseDto> addExpense(@Valid @RequestBody ExpenseRequestDto request) {
        ExpenseResponseDto expenseResponseDto = expenseTrackingCrudFacade.addExpense(request);
        CreateExpenseResponseDto createExpenseResponseDto = mapFromExpenseResponseDtoToCreateExpenseResponseDto(expenseResponseDto);
        return ResponseEntity.ok(createExpenseResponseDto);
    }

    @Operation(summary = "Get all expenses", description = "Retrieves a list of all expenses")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all expenses")
    ResponseEntity<GetAllExpensesResponseDto> getAllExpenses() {
        List<ExpenseResponseDto> expenses = expenseTrackingCrudFacade.getAllExpenses();
        GetAllExpensesResponseDto response = mapToGetAllExpensesResponseDto(expenses);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{expenseId}")
    @Operation(summary = "Update an expense", description = "Updates an existing expense")
    @ApiResponse(responseCode = "200", description = "Successfully updated the expense")
    @ApiResponse(responseCode = "404", description = "Expense not found")
    ResponseEntity<UpdateExpenseResponseDto> updateExpense(@Parameter(description = "ID of the expense to update") @PathVariable Long expenseId,
                                                           @Valid @RequestBody ExpenseRequestDto request) {
        ExpenseResponseDto updatedExpense = expenseTrackingCrudFacade.updateExpense(expenseId, request);
        UpdateExpenseResponseDto response = ExpenseRestControllerMapper.mapFromExpenseResponseDtotoUpdateExpenseResponseDto(updatedExpense);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{expenseId}")
    @Operation(summary = "Delete an expense", description = "Deletes an existing expense")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the expense")
    @ApiResponse(responseCode = "404", description = "Expense not found")
    ResponseEntity<DeleteExpenseResponseDto> deleteExpense(@Parameter(description = "ID of the expense to delete") @PathVariable Long expenseId) {
        expenseTrackingCrudFacade.deleteExpense(expenseId);
        DeleteExpenseResponseDto response = mapFromExpenseToDeleteExpenseResponseDto(expenseId);
        return ResponseEntity.ok(response);
    }

}

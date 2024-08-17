package com.example.expensetracking.infrastructure.budgeting.controller;

import com.example.expensetracking.domain.budgeting.BudgetingFacade;
import com.example.expensetracking.domain.budgeting.dto.AddExpenseToBudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetRequestDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.expensetracking.infrastructure.budgeting.controller.BudgetingControllerMapper.mapToGetAllBudgetsResponseDto;

@RestController
@RequestMapping("/budgets")
@Tag(name = "Budgets", description = "Endpoints for managing budgets")
public class BudgetingController {

    private final BudgetingFacade budgetingFacade;

    public BudgetingController(BudgetingFacade budgetingFacade) {
        this.budgetingFacade = budgetingFacade;
    }

    @PostMapping
    @Operation(summary = "Create a new budget", description = "Creates a new budget with specified categories and limits")
    @ApiResponse(responseCode = "200", description = "Successfully created budget")
    public ResponseEntity<BudgetResponseDto> createBudget(@Valid @RequestBody BudgetRequestDto request) {
        BudgetResponseDto response = budgetingFacade.createBudget(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<BudgetResponseDto> getBudgetById(@PathVariable Long budgetId) {
        BudgetResponseDto budget = budgetingFacade.getBudgetById(budgetId);
        return ResponseEntity.ok(budget);
    }


//    @PostMapping("/{budgetId}/expense")
//    @Operation(summary = "Add expense to budget", description = "Adds a new expense to the specified budget")
//    @ApiResponse(responseCode = "200", description = "Successfully added expense to budget")
//    @ApiResponse(responseCode = "404", description = "Budget not found")
//    @ApiResponse(responseCode = "400", description = "Insufficient budget remaining")
//    public ResponseEntity<AddExpenseToBudgetResponseDto> addExpenseToBudget(
//            @PathVariable Long budgetId,
//            @Valid @RequestBody AddExpenseToBudgetRequestDto request) {
//        AddExpenseToBudgetResponseDto response = budgetingFacade.addExpenseToBudget(budgetId, request);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/{budgetId}/add-existing-expense/{expenseId}")
    public ResponseEntity<AddExpenseToBudgetResponseDto> addExistingExpenseToBudget(
            @PathVariable Long budgetId,
            @PathVariable Long expenseId
    ) {
        AddExpenseToBudgetResponseDto response = budgetingFacade.addExistingExpenseToBudget(budgetId, expenseId);
        return ResponseEntity.ok(response);
    }


    @GetMapping()
    @Operation(summary = "Get budget summary", description = "Retrieves a summary of the active budgets")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved budget summaries")
    @ApiResponse(responseCode = "404", description = "No budgets found")
    public ResponseEntity<GetAllBudgetsResponseDto> getBudgetSummary() {
        List<BudgetSummaryDto> activeBudgets = budgetingFacade.summarizeActiveBudgets();
        GetAllBudgetsResponseDto response = mapToGetAllBudgetsResponseDto(activeBudgets);
        return ResponseEntity.ok(response);
    }


}

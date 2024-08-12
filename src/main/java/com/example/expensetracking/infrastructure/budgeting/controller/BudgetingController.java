package com.example.expensetracking.infrastructure.budgeting.controller;

import com.example.expensetracking.domain.budgeting.BudgetingFacade;
import com.example.expensetracking.domain.budgeting.dto.BudgetRequestDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
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

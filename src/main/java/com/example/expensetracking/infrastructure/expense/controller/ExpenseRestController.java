package com.example.expensetracking.infrastructure.expense.controller;

import com.example.expensetracking.domain.expense.crud.ExpenseFacade;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.crud.dto.ExpenseResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
class ExpenseRestController {

    private final ExpenseFacade expenseFacade;

    ExpenseRestController(ExpenseFacade expenseFacade) {
        this.expenseFacade = expenseFacade;
    }

    @GetMapping("/{expenseId}")
    ResponseEntity<GetExpenseResponseDto> getExpenseById(@PathVariable Long expenseId) {
        ExpenseResponseDto expenseResponseDto = expenseFacade.getExpenseById(expenseId);
        GetExpenseResponseDto getExpenseResponseDto = ExpenseRestControllerMapper.mapFromExpenseResponseDtoToGetExpenseResponseDto(expenseResponseDto);
        return ResponseEntity.ok(getExpenseResponseDto);
    }

    @PostMapping("/{expenseId}")
    ResponseEntity<CreateExpenseResponseDto> addExpense(@Valid @RequestBody ExpenseRequestDto request) {
        ExpenseResponseDto expenseResponseDto = expenseFacade.addExpense(request);
        CreateExpenseResponseDto createExpenseResponseDto = ExpenseRestControllerMapper.mapFromExpenseResponseDtoToCreateExpenseResponseDto(expenseResponseDto);
        return ResponseEntity.ok(createExpenseResponseDto);
    }


}

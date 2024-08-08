package com.example.expensetracking.infrastructure.crud.controller;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.dto.CreateExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.dto.GetExpenseResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.expensetracking.infrastructure.crud.controller.ExpenseRestControllerMapper.mapFromExpenseResponseDtoToCreateExpenseResponseDto;
import static com.example.expensetracking.infrastructure.crud.controller.ExpenseRestControllerMapper.mapFromExpenseResponseDtoToGetExpenseResponseDto;

@RestController
@RequestMapping("/expenses")
class ExpenseRestController {

    private final ExpenseTrackingCrudFacade expenseTrackingCrudFacade;

    ExpenseRestController(ExpenseTrackingCrudFacade expenseTrackingCrudFacade) {
        this.expenseTrackingCrudFacade = expenseTrackingCrudFacade;
    }

    @GetMapping("/{expenseId}")
    ResponseEntity<GetExpenseResponseDto> getExpenseById(@PathVariable Long expenseId) {
        ExpenseResponseDto expenseResponseDto = expenseTrackingCrudFacade.getExpenseById(expenseId);
        GetExpenseResponseDto getExpenseResponseDto = mapFromExpenseResponseDtoToGetExpenseResponseDto(expenseResponseDto);
        return ResponseEntity.ok(getExpenseResponseDto);
    }

    @PostMapping()
    ResponseEntity<CreateExpenseResponseDto> addExpense(@Valid @RequestBody ExpenseRequestDto request) {
        ExpenseResponseDto expenseResponseDto = expenseTrackingCrudFacade.addExpense(request);
        CreateExpenseResponseDto createExpenseResponseDto = mapFromExpenseResponseDtoToCreateExpenseResponseDto(expenseResponseDto);
        return ResponseEntity.ok(createExpenseResponseDto);
    }


}

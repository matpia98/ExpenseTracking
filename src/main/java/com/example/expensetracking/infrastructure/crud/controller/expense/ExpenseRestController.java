package com.example.expensetracking.infrastructure.crud.controller.expense;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.CreateExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.DeleteExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetAllExpensesResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetExpenseResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.UpdateExpenseResponseDto;
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

    @GetMapping
    ResponseEntity<GetAllExpensesResponseDto> getAllExpenses() {
        List<ExpenseResponseDto> expenses = expenseTrackingCrudFacade.getAllExpenses();
        GetAllExpensesResponseDto response = mapToGetAllExpensesResponseDto(expenses);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{expenseId}")
    ResponseEntity<UpdateExpenseResponseDto> updateExpense(@PathVariable Long expenseId, @Valid @RequestBody ExpenseRequestDto request) {
        ExpenseResponseDto updatedExpense = expenseTrackingCrudFacade.updateExpense(expenseId, request);
        UpdateExpenseResponseDto response = ExpenseRestControllerMapper.mapFromExpenseResponseDtotoUpdateExpenseResponseDto(updatedExpense);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{expenseId}")
    ResponseEntity<DeleteExpenseResponseDto> deleteExpense(@PathVariable Long expenseId) {
        expenseTrackingCrudFacade.deleteExpense(expenseId);
        DeleteExpenseResponseDto response = mapFromExpenseToDeleteExpenseResponseDto(expenseId);
        return ResponseEntity.ok(response);
    }

}

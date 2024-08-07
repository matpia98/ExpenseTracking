package com.example.expensetracking.domain.expense.crud;

import com.example.expensetracking.domain.expense.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.expense.dto.ExpenseResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ExpenseUpdater {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {
        Expense expense = expenseMapper.mapFromExponseRequestToExponse(expenseRequestDto);
        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.mapFromExponseToExponseResponse(savedExpense);

    }
}
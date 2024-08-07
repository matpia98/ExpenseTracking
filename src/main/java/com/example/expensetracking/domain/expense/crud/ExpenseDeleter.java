package com.example.expensetracking.domain.expense.crud;

import org.springframework.stereotype.Service;

@Service
class ExpenseDeleter {

    private final ExpenseRepository expenseRepository;

    ExpenseDeleter(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    void deleteById(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Expense with id " + id + " not found");
        }
        expenseRepository.deleteById(id);
    }
}

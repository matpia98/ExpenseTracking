package com.example.expensetracking.domain.expense.crud;

import org.springframework.data.jpa.repository.JpaRepository;

interface ExpenseRepository extends JpaRepository<Expense, Long> {
}

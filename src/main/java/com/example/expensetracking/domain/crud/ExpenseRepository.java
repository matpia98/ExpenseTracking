package com.example.expensetracking.domain.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByCategoryId(Long categoryId);
    boolean existsByCategoryId(Long categoryId);
}

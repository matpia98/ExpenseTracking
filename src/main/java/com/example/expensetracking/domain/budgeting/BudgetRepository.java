package com.example.expensetracking.domain.budgeting;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findAllByEndDateAfter(LocalDate date);

}

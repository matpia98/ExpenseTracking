package com.example.expensetracking.domain.category.crud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface CategoryRepository extends JpaRepository<Category, Long> {

}

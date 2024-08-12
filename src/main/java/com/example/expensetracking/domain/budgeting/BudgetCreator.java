package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.budgeting.dto.BudgetRequestDto;
import com.example.expensetracking.domain.budgeting.dto.BudgetResponseDto;
import com.example.expensetracking.domain.budgeting.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class BudgetCreator {

    private final BudgetRepository budgetRepository;

    BudgetCreator(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    BudgetResponseDto createBudget(BudgetRequestDto requestDto) {
        Budget budget = Budget.builder()
                .startDate(requestDto.startDate())
                .endDate(requestDto.endDate())
                .build();

        requestDto.categories().forEach(category ->
                budget.addCategory(BudgetCategory.builder()
                        .name(category.name())
                        .budgetLimit(category.budgetLimit())
                        .build())
        );

        Budget savedBudget = budgetRepository.save(budget);
        return mapToBudgetResponseDto(savedBudget);
    }

    private BudgetResponseDto mapToBudgetResponseDto(Budget budget) {
        List<CategoryDto> categories = budget.getCategories().stream()
                .map(category -> new CategoryDto(category.getName(), category.getBudgetLimit()))
                .collect(Collectors.toList());

        return new BudgetResponseDto(budget.getId(), budget.getStartDate(), budget.getEndDate(), categories);
    }

}

package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.ExpenseRequestDto;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
class ExpenseAdder {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryRepository categoryRepository;
    private final CurrencyConversionService currencyConversionService;
    public static final String DEFAULT_CURRENCY = "PLN";

    ExpenseAdder(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, CategoryRepository categoryRepository, CurrencyConversionService currencyConversionService) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.categoryRepository = categoryRepository;
        this.currencyConversionService = currencyConversionService;
    }

    ExpenseResponseDto addExpense(ExpenseRequestDto expenseRequestDto) {
        Category category = categoryRepository.findById(expenseRequestDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + expenseRequestDto.categoryId() + " not found"));

        String currency = expenseRequestDto.currency() != null ? expenseRequestDto.currency() : DEFAULT_CURRENCY;
        BigDecimal amount = BigDecimal.valueOf(expenseRequestDto.amount());

        if (!currency.equals(DEFAULT_CURRENCY)) {
            amount = currencyConversionService.convertCurrency(amount, currency, DEFAULT_CURRENCY);
        }

        ExpenseRequestDto convertedRequest = new ExpenseRequestDto(
                expenseRequestDto.title(),
                expenseRequestDto.description(),
                amount.doubleValue(),
                expenseRequestDto.categoryId(),
                DEFAULT_CURRENCY
        );

        Expense expenseToSave = expenseMapper.mapFromExpenseRequestToExpense(convertedRequest);
        expenseToSave.setCategory(category);
        category.addExpense(expenseToSave);
        Expense savedExpense = expenseRepository.save(expenseToSave);

        return expenseMapper.mapFromExpenseToExpenseResponse(savedExpense);
    }
}
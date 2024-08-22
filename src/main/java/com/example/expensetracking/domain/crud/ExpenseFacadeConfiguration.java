package com.example.expensetracking.domain.crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ExpenseFacadeConfiguration {

    @Bean
    ExpenseTrackingCrudFacade expenseFacade(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, CurrencyConvertable currencyConvertable) {
        ExpenseMapper expenseMapper = new ExpenseMapper();
        CurrencyConversionService currencyConversionService = new CurrencyConversionService(currencyConvertable);
        ExpenseAdder expenseAdder = new ExpenseAdder(expenseRepository, expenseMapper, categoryRepository, currencyConversionService);
        ExpenseRetriever expenseRetriever = new ExpenseRetriever(expenseRepository, expenseMapper);
        ExpenseDeleter expenseDeleter = new ExpenseDeleter(expenseRepository);
        ExpenseUpdater expenseUpdater = new ExpenseUpdater(expenseRepository, expenseMapper, categoryRepository);
        CategoryMapper categoryMapper = new CategoryMapper();
        CategoryAdder categoryAdder = new CategoryAdder(categoryRepository, categoryMapper);
        CategoryRetriever categoryRetriever = new CategoryRetriever(categoryRepository, categoryMapper);
        CategoryDeleter categoryDeleter = new CategoryDeleter(categoryRepository);
        CategoryUpdater categoryUpdater = new CategoryUpdater(categoryRepository, categoryMapper);
        return new ExpenseTrackingCrudFacade(
                expenseAdder,
                expenseRetriever,
                expenseDeleter,
                expenseUpdater,
                categoryAdder,
                categoryRetriever,
                categoryDeleter,
                categoryUpdater
        );
    }
}

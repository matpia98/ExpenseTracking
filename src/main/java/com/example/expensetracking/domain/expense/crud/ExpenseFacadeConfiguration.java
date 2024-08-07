package com.example.expensetracking.domain.expense.crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseFacadeConfiguration {

    @Bean
    ExpenseFacade expenseFacade(ExpenseRepository expenseRepository) {
        ExpenseMapper expenseMapper = new ExpenseMapper();
        ExpenseAdder expenseAdder = new ExpenseAdder(expenseRepository, expenseMapper);
        ExpenseRetriever expenseRetriever = new ExpenseRetriever(expenseRepository, expenseMapper);
        ExpenseDeleter expenseDeleter = new ExpenseDeleter(expenseRepository);
        ExpenseUpdater expenseUpdater = new ExpenseUpdater(expenseRepository, expenseMapper);
        return new ExpenseFacade(expenseAdder, expenseRetriever, expenseDeleter, expenseUpdater);
    }
}

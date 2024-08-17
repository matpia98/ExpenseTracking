package com.example.expensetracking.domain.budgeting;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BudgetingFacadeConfiguration {

    @Bean
    BudgetingFacade budgetingFacade(ExpenseTrackingCrudFacade crudFacade, BudgetRepository budgetRepository) {
        BudgetCreator budgetCreator = new BudgetCreator(budgetRepository);
        BudgetSummarizer budgetSummarizer = new BudgetSummarizer(budgetRepository);
        BudgetExpenseAdder budgetExpenseAdder = new BudgetExpenseAdder(budgetRepository);
        BudgetRetriever budgetRetriever = new BudgetRetriever(budgetRepository);
        return new BudgetingFacade(budgetCreator, budgetSummarizer, budgetExpenseAdder, budgetRetriever, crudFacade);
    }
}

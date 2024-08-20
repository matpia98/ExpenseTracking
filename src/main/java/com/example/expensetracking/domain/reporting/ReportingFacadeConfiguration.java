package com.example.expensetracking.domain.reporting;

import com.example.expensetracking.domain.crud.ExpenseTrackingCrudFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ReportingFacadeConfiguration {

    @Bean
    ReportingFacade reportingFacade(ExpenseTrackingCrudFacade crudFacade, Clock clock) {
        ReportGenerator reportGenerator = new ReportGenerator();
        return new ReportingFacade(crudFacade, reportGenerator, clock);
    }

}

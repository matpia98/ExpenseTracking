package com.example.expensetracking;

import com.example.expensetracking.infrastructure.crud.http.ExchangeRateClientConfigurationProperties;
import com.example.expensetracking.infrastructure.security.jwt.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtConfigurationProperties.class, ExchangeRateClientConfigurationProperties.class})
public class ExpenseTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackingApplication.class, args);
    }

}

package com.example.expensetracking.infrastructure.crud.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchange-rate.http.client.config")
public record ExchangeRateClientConfigurationProperties(
        String uri,
        int port,
        int connectionTimeout,
        int readTimeout
) {
}
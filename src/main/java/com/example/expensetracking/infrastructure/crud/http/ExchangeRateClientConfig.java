package com.example.expensetracking.infrastructure.crud.http;

import com.example.expensetracking.domain.crud.CurrencyConvertable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ExchangeRateClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler,
                                     ExchangeRateClientConfigurationProperties properties) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }

    public CurrencyConvertable exchangeRateRestTemplateClientTestConfig(RestTemplate restTemplate,
                                                                        String uri, int port) {
        return new ExchangeRateClient(restTemplate, uri, port);
    }

    @Bean
    public CurrencyConvertable exchangeRateClient(RestTemplate restTemplate,
                                                  ExchangeRateClientConfigurationProperties properties) {
        return new ExchangeRateClient(restTemplate, properties.uri(), properties.port());
    }
}
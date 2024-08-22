package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.ExchangeRateResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
class CurrencyConversionService {

    private final CurrencyConvertable exchangeRateClient;

    public CurrencyConversionService(CurrencyConvertable exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        ExchangeRateResponse rates = exchangeRateClient.fetchExchangeRates();
        Double fromRate = rates.getRate(fromCurrency);
        Double toRate = rates.getRate(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException("Invalid currency");
        }

        return amount.multiply(BigDecimal.valueOf(toRate / fromRate))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
package com.example.expensetracking.domain.crud.dto;

import java.util.Map;

public record ExchangeRateResponse(
    String base,
    Map<String, Double> rates
) {

    public Double getRate(String currency) {
        return rates.get(currency);
    }
}
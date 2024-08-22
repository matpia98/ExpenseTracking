package com.example.expensetracking.domain.crud;

import com.example.expensetracking.domain.crud.dto.ExchangeRateResponse;

public interface CurrencyConvertable {

    ExchangeRateResponse fetchExchangeRates();
}

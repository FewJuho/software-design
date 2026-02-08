package com.example.currencyrateprovider.domain;

import java.util.Objects;

/**
 * Value object representing a currency pair (e.g., USD/RUB).
 */
public record CurrencyPair(String fromCurrency, String toCurrency) {

    public CurrencyPair {
        Objects.requireNonNull(fromCurrency, "fromCurrency must not be null");
        Objects.requireNonNull(toCurrency, "toCurrency must not be null");

        if (fromCurrency.isBlank() || toCurrency.isBlank()) {
            throw new IllegalArgumentException("Currency codes must not be blank");
        }

        fromCurrency = fromCurrency.toUpperCase().trim();
        toCurrency = toCurrency.toUpperCase().trim();
    }
}
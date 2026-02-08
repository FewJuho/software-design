package com.example.currencyrateprovider.domain;

import java.time.Instant;

/**
 * Value object representing a currency exchange rate at a specific moment.
 */
public record CurrencyRate(CurrencyPair pair, double rate, Instant timestamp) {

    public CurrencyRate {
        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be positive, got: " + rate);
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null");
        }
    }
}
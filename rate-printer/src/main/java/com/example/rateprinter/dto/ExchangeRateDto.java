package com.example.rateprinter.dto;

/**
 * Immutable DTO carrying exchange rate data from the remote service.
 */
public record ExchangeRateDto(
    String fromCurrency,
    String toCurrency,
    double rate,
    String timestamp
) {

    /**
     * Human-readable representation for console output.
     */
    public String toDisplayString() {
        return String.format("[%s] %s/%s = %.2f", timestamp, fromCurrency, toCurrency, rate);
    }
}
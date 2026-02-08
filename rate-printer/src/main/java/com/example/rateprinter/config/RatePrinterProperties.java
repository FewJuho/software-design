package com.example.rateprinter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration for which currency pair to poll.
 */
@ConfigurationProperties(prefix = "rate-printer")
public record RatePrinterProperties(
    String fromCurrency,
    String toCurrency
) {
    public RatePrinterProperties {
        if (fromCurrency == null || fromCurrency.isBlank()) fromCurrency = "USD";
        if (toCurrency == null || toCurrency.isBlank()) toCurrency = "RUB";
    }
}
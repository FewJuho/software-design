package com.example.currencyrateprovider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Externalized configuration for base exchange rates.
 * Key format: "USDRUB", "EURRUB", etc.
 */
@ConfigurationProperties(prefix = "currency")
public record BaseRateProperties(Map<String, Double> baseRates) {

    public double getBaseRate(String pairKey) {
        Double rate = baseRates.get(pairKey.toUpperCase());
        if (rate == null) {
            throw new IllegalArgumentException("Unsupported currency pair: " + pairKey);
        }
        return rate;
    }

    public boolean supports(String pairKey) {
        return baseRates.containsKey(pairKey.toUpperCase());
    }
}
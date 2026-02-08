package com.example.rateprinter.client;

import com.example.rateprinter.dto.ExchangeRateDto;

/**
 * Abstraction for fetching exchange rates from a remote provider.
 * The scheduler depends on this interface, not on gRPC directly.
 */
public interface CurrencyRateClient {

    /**
     * Fetches the current exchange rate for the given currency pair.
     *
     * @param fromCurrency source currency code (e.g., "USD")
     * @param toCurrency   target currency code (e.g., "RUB")
     * @return the exchange rate data
     */
    ExchangeRateDto fetchRate(String fromCurrency, String toCurrency);
}
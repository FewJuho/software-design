package com.example.currencyrateprovider.service;

import com.example.currencyrateprovider.domain.CurrencyPair;
import com.example.currencyrateprovider.domain.CurrencyRate;

/**
 * Abstraction for providing currency exchange rates.
 * Follows Interface Segregation — only one responsibility.
 */
public interface CurrencyRateProvider {

    /**
     * Returns the current exchange rate for the given currency pair.
     *
     * @param pair the currency pair
     * @return the current rate with timestamp
     * @throws IllegalArgumentException if the pair is not supported
     */
    CurrencyRate getRate(CurrencyPair pair);
}
package com.example.currencyrateprovider.service;

/**
 * Strategy for applying random fluctuation to a base rate.
 * Open for extension — implement a different strategy without modifying consumers.
 */
public interface RandomFluctuationStrategy {

    /**
     * Applies fluctuation to the given base rate.
     *
     * @param baseRate the base exchange rate
     * @return the fluctuated rate
     */
    double apply(double baseRate);
}
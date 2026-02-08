package com.example.currencyrateprovider.service;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

/**
 * Applies a random fluctuation within ±2% of the base rate.
 */
@Component
public class PercentageFluctuationStrategy implements RandomFluctuationStrategy {

    private static final double MAX_DEVIATION_PERCENT = 2.0;

    @Override
    public double apply(double baseRate) {
        double deviationFactor = 1.0 + ThreadLocalRandom.current()
            .nextDouble(-MAX_DEVIATION_PERCENT, MAX_DEVIATION_PERCENT) / 100.0;
        return Math.round(baseRate * deviationFactor * 100.0) / 100.0;
    }
}
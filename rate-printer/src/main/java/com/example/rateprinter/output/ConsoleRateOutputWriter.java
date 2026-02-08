package com.example.rateprinter.output;

import com.example.rateprinter.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;

/**
 * Writes exchange rate information to the console (stdout).
 */
@Component
public class ConsoleRateOutputWriter implements RateOutputWriter {

    @Override
    public void write(ExchangeRateDto rate) {
        System.out.println("💱 " + rate.toDisplayString());
    }
}
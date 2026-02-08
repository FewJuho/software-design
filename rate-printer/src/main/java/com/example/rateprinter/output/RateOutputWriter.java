package com.example.rateprinter.output;

import com.example.rateprinter.dto.ExchangeRateDto;

/**
 * Abstraction for outputting exchange rate information.
 * Can be swapped to file writer, messaging, etc. (OCP).
 */
public interface RateOutputWriter {

    void write(ExchangeRateDto rate);
}
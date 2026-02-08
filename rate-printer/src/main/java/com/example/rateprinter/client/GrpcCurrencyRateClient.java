package com.example.rateprinter.client;

import com.example.rateprinter.dto.ExchangeRateDto;
import com.example.rateprinter.grpc.CurrencyRateServiceGrpc;
import com.example.rateprinter.grpc.RateRequest;
import com.example.rateprinter.grpc.RateResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * gRPC adapter implementing the CurrencyRateClient interface.
 */
@Component
public class GrpcCurrencyRateClient implements CurrencyRateClient {

    private static final Logger log = LoggerFactory.getLogger(GrpcCurrencyRateClient.class);

    @GrpcClient("currency-rate-provider")
    private CurrencyRateServiceGrpc.CurrencyRateServiceBlockingStub rateStub;

    @Override
    public ExchangeRateDto fetchRate(String fromCurrency, String toCurrency) {
        log.debug("Requesting rate for {}/{}", fromCurrency, toCurrency);

        RateRequest request = RateRequest.newBuilder()
            .setFromCurrency(fromCurrency)
            .setToCurrency(toCurrency)
            .build();

        RateResponse response = rateStub.getRate(request);

        return new ExchangeRateDto(
            response.getFromCurrency(),
            response.getToCurrency(),
            response.getRate(),
            response.getTimestamp()
        );
    }
}
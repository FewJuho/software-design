package com.example.rateprinter.client;

import com.example.rateprinter.dto.ExchangeRateDto;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpCurrencyRateClient implements CurrencyRateClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public HttpCurrencyRateClient(String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ExchangeRateDto fetchRate(String fromCurrency, String toCurrency) {
        String url = baseUrl + "/api/rates?from={from}&to={to}";

        Map<String, Object> response = restTemplate.getForObject(
            url,
            Map.class,
            fromCurrency,
            toCurrency
        );

        if (response == null)
            throw new IllegalStateException("Received null response from provider");

        return new ExchangeRateDto(
            (String) response.get("fromCurrency"),
            (String) response.get("toCurrency"),
            ((Number) response.get("rate")).doubleValue(),
            (String) response.get("timestamp")
        );
    }
}
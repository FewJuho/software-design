package com.example.rateprinter.client;

import com.example.rateprinter.grpc.ServiceInstanceProvider;
import com.example.rateprinter.dto.ExchangeRateDto;
import com.example.rateprinter.grpc.CurrencyRateServiceGrpc;
import com.example.rateprinter.grpc.RateRequest;
import com.example.rateprinter.grpc.RateResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GrpcCurrencyRateClient implements CurrencyRateClient {

    private static final Logger log = LoggerFactory.getLogger(GrpcCurrencyRateClient.class);
    private static final String SERVICE_NAME = "currency-rate-provider";

    private final ServiceInstanceProvider instanceProvider;

    public GrpcCurrencyRateClient(ServiceInstanceProvider instanceProvider) {
        this.instanceProvider = instanceProvider;
    }

    @Override
    public ExchangeRateDto fetchRate(String fromCurrency, String toCurrency) {
        ServiceInstance<String> instance = instanceProvider.getInstance(SERVICE_NAME)
            .orElseThrow(() -> new IllegalStateException(
                "No available instances of " + SERVICE_NAME));

        String target = instance.getAddress() + ":" + instance.getPort();
        log.info("📡 Connecting to provider instance: {}", target);

        ManagedChannel channel = ManagedChannelBuilder
            .forAddress(instance.getAddress(), instance.getPort())
            .usePlaintext()
            .build();

        try {
            CurrencyRateServiceGrpc.CurrencyRateServiceBlockingStub stub =
                CurrencyRateServiceGrpc.newBlockingStub(channel);

            RateRequest request = RateRequest.newBuilder()
                .setFromCurrency(fromCurrency)
                .setToCurrency(toCurrency)
                .build();

            RateResponse response = stub.getRate(request);

            return new ExchangeRateDto(
                response.getFromCurrency(),
                response.getToCurrency(),
                response.getRate(),
                response.getTimestamp()
            );
        } finally {
            channel.shutdownNow();
        }
    }
}
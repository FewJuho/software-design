package com.example.currencyrateprovider.grpc;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class GrpcServiceRegistrar {
    private static final String SERVICE_NAME = "currency-rate-provider";

    private final ServiceDiscovery<String> serviceDiscovery;
    private final int grpcPort;

    public GrpcServiceRegistrar(
        ServiceDiscovery<String> serviceDiscovery,
        @Value("${grpc.server.port}") int grpcPort) {
        this.serviceDiscovery = serviceDiscovery;
        this.grpcPort = grpcPort;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void registerService() throws Exception {
        String host = InetAddress.getLocalHost().getHostAddress();

        ServiceInstance<String> instance = ServiceInstance.<String>builder()
            .name(SERVICE_NAME)
            .address(host)
            .port(grpcPort)
            .payload("gRPC currency rate provider")
            .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
            .build();

        serviceDiscovery.registerService(instance);
    }
}
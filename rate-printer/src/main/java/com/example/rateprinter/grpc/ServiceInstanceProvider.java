package com.example.rateprinter.grpc;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Optional;

public interface ServiceInstanceProvider {
    Optional<ServiceInstance<String>> getInstance(String serviceName);
}
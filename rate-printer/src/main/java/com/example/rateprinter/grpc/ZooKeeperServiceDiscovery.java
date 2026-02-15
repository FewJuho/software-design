package com.example.rateprinter.grpc;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ZooKeeperServiceDiscovery implements ServiceInstanceProvider {

    private static final Logger log = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);

    private final ServiceDiscovery<String> serviceDiscovery;
    private final com.example.rateprinter.grpc.RoundRobinLoadBalancer loadBalancer;

    public ZooKeeperServiceDiscovery(ServiceDiscovery<String> serviceDiscovery,
        com.example.rateprinter.grpc.RoundRobinLoadBalancer loadBalancer) {
        this.serviceDiscovery = serviceDiscovery;
        this.loadBalancer = loadBalancer;
    }

    @Override
    public Optional<ServiceInstance<String>> getInstance(String serviceName) {
        List<ServiceInstance<String>> instances = fetchInstances(serviceName);

        if (instances.isEmpty()) {
            log.warn("No instances found for service: {}", serviceName);
            return Optional.empty();
        }

        log.debug("Found {} instance(s) for '{}', selecting via round-robin",
            instances.size(), serviceName);

        return Optional.of(loadBalancer.select(instances));
    }

    private List<ServiceInstance<String>> fetchInstances(String serviceName) {
        try {
            Collection<ServiceInstance<String>> instances =
                serviceDiscovery.queryForInstances(serviceName);
            return List.copyOf(instances);
        } catch (Exception e) {
            log.error("Failed to query ZooKeeper for service '{}': {}",
                serviceName, e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
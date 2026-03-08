package com.example.rateprinter.grpc;

import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe Round-Robin load balancer.
 */
@Component
public class RoundRobinLoadBalancer {

    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Selects the next instance from the list using round-robin.
     *
     * @param instances non-empty list of available instances
     * @return the selected instance
     * @throws IllegalArgumentException if the list is empty
     */
    public ServiceInstance<String> select(List<ServiceInstance<String>> instances) {
        if (instances.isEmpty())
            throw new IllegalArgumentException("Cannot select from an empty instance list");

        int idx = Math.abs(counter.getAndIncrement() % instances.size());
        return instances.get(idx);
    }
}
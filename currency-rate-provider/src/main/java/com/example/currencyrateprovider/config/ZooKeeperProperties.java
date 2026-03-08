package com.example.currencyrateprovider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zookeeper")
public record ZooKeeperProperties(
    String connectString,
    String servicePath,
    int sessionTimeoutMs,
    int connectionTimeoutMs
) {
    public ZooKeeperProperties {
        if (connectString == null || connectString.isBlank())
            connectString = "localhost:2181";

        if (servicePath == null || servicePath.isBlank())
            servicePath = "/services";

        if (sessionTimeoutMs <= 0) sessionTimeoutMs = 30000;
        if (connectionTimeoutMs <= 0) connectionTimeoutMs = 15000;
    }
}
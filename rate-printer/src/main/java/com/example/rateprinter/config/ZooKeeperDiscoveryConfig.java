package com.example.rateprinter.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZooKeeperDiscoveryConfig {

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework curatorFramework(ZooKeeperProperties properties) {
        return CuratorFrameworkFactory.builder()
            .connectString(properties.connectString())
            .sessionTimeoutMs(properties.sessionTimeoutMs())
            .connectionTimeoutMs(properties.connectionTimeoutMs())
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    public ServiceDiscovery<String> serviceDiscovery(
        CuratorFramework curatorFramework,
        ZooKeeperProperties properties) {

        return ServiceDiscoveryBuilder.builder(String.class)
            .client(curatorFramework)
            .basePath(properties.servicePath())
            .serializer(new JsonInstanceSerializer<>(String.class))
            .build();
    }
}
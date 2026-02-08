package com.example.currencyrateprovider.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BaseRateProperties.class)
public class AppConfig {
}
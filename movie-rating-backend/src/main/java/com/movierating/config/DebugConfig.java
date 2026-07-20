package com.movierating.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    CommandLineRunner printDbUrl() {
        return args -> System.out.println("DATABASE URL = " + url);
    }
}

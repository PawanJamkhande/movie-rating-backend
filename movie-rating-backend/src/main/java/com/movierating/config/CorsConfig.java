package com.movierating.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // FRONTEND_URL env var lets you add your deployed frontend (Vercel/Netlify)
    // without touching code - localhost:4200 always stays allowed for local dev.
    @Value("${FRONTEND_URL:}")
    private String frontendUrl;

    @Bean
    CorsFilter corsFilter() {

        CorsConfiguration configuration = new CorsConfiguration();

        List<String> allowedOrigins = new java.util.ArrayList<>();
        allowedOrigins.add("http://localhost:4200");
        allowedOrigins.add("https://movie-rating-frontend-79j7.vercel.app");
        if (frontendUrl != null && !frontendUrl.isBlank()) {
            allowedOrigins.add(frontendUrl);
        }
        configuration.setAllowedOrigins(allowedOrigins);

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);

    }

}


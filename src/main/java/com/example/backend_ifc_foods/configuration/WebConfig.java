package com.example.backend_ifc_foods.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autoriser tous les endpoints
                .allowedOrigins(
                        "http://localhost:4200", // Origine Angular locale
                        "https://5196-102-244-45-118.ngrok-free.app" // Domaine statique via ngrok
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes autorisées
                .allowedHeaders("*") // Autoriser tous les en-têtes
                .allowCredentials(true); // Autoriser les cookies (si nécessaire)
    }
}

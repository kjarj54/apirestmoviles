package com.restapi.apirestmoviles.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT","*")
                .allowedOrigins("http://127.0.0.1:5500","*","http://apirestmoviles-production.up.railway.app:5500","http://apirestmoviles-production.up.railway.app:8080","https://apirestmoviles-production.up.railway.app")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
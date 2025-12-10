package com.greenlog.greenlog_solutions_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Mapeia para todas as rotas (endpoints) da sua API
        registry.addMapping("/**") 
                // Permite requisições originadas do seu Front-end Angular (porta padrão 4200)
                .allowedOrigins("http://localhost:4200") 
                // Define os métodos HTTP que são permitidos (CRUD operations)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Permite todos os cabeçalhos nas requisições
                .allowedHeaders("*"); 
    }
}
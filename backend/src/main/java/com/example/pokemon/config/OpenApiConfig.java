package com.example.pokemon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the backend's manual testing surface.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pokemonTeamBuilderOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Pokemon Team Builder API")
                .version("v1")
                .description("Spring Boot API for single Pokemon lookup and deterministic team analysis."));
    }
}

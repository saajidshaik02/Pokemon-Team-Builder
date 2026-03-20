package com.example.pokemon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Spring configuration for outbound HTTP client beans.
 *
 * <p>This configuration creates the {@link RestClient} used by the PokeAPI client
 * so the external base URL is defined in configuration rather than hardcoded.</p>
 */
@Configuration
public class RestClientConfig {

    /**
     * Creates the REST client used for PokeAPI communication.
     *
     * @param restClientBuilder Spring-provided RestClient builder
     * @param pokeApiBaseUrl configured PokeAPI base URL
     * @return configured RestClient pointed at the PokeAPI base URL
     */
    @Bean
    public RestClient pokeApiRestClient(
            RestClient.Builder restClientBuilder,
            @Value("${pokemon.api.base-url}") String pokeApiBaseUrl
    ) {
        return restClientBuilder
                .baseUrl(pokeApiBaseUrl)
                .build();
    }
}

package com.example.pokemon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

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

package com.example.pokemon.client;

import com.example.pokemon.exception.ExternalServiceException;
import com.example.pokemon.exception.PokemonNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class PokeApiClient {

    private final RestClient pokeApiRestClient;

    public PokeApiClient(@Qualifier("pokeApiRestClient") RestClient pokeApiRestClient) {
        this.pokeApiRestClient = pokeApiRestClient;
    }

    public PokeApiPokemonResponse getPokemonByName(String normalizedName) {
        try {
            PokeApiPokemonResponse response = pokeApiRestClient.get()
                    .uri("/pokemon/{name}", normalizedName)
                    .retrieve()
                    .body(PokeApiPokemonResponse.class);

            if (response == null) {
                throw new ExternalServiceException("PokeAPI returned an empty response.");
            }

            return response;
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().value() == 404) {
                throw new PokemonNotFoundException(normalizedName);
            }
            throw new ExternalServiceException("Failed to load Pokemon data from PokeAPI.", exception);
        } catch (ResourceAccessException exception) {
            throw new ExternalServiceException("PokeAPI is currently unavailable.", exception);
        }
    }
}

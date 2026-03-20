package com.example.pokemon.client;

import com.example.pokemon.exception.ExternalServiceException;
import com.example.pokemon.exception.PokemonNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

/**
 * Client responsible for fetching raw Pokemon data from PokeAPI.
 *
 * <p>This class isolates external HTTP communication from the service layer and
 * converts low-level client failures into application-specific exceptions.</p>
 */
@Component
public class PokeApiClient {

    private final RestClient pokeApiRestClient;

    /**
     * Creates a new PokeAPI client.
     *
     * @param pokeApiRestClient configured REST client pointing at the PokeAPI base URL
     */
    public PokeApiClient(@Qualifier("pokeApiRestClient") RestClient pokeApiRestClient) {
        this.pokeApiRestClient = pokeApiRestClient;
    }

    /**
     * Fetches raw Pokemon data by normalized name.
     *
     * @param normalizedName normalized lowercase Pokemon name used in the upstream request
     * @return raw PokeAPI Pokemon response data
     * @throws PokemonNotFoundException if the Pokemon does not exist upstream
     * @throws ExternalServiceException if the upstream service is unavailable or returns an unusable payload
     */
    public PokeApiPokemonResponse getPokemonByName(String normalizedName) {
        try {
            PokeApiPokemonResponse response = pokeApiRestClient.get()
                    .uri("/pokemon/{name}", normalizedName)
                    .retrieve()
                    .body(PokeApiPokemonResponse.class);

            if (response == null) {
                throw new ExternalServiceException("Pokemon data could not be loaded from PokeAPI.");
            }

            return response;
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().value() == 404) {
                throw new PokemonNotFoundException(normalizedName);
            }
            throw new ExternalServiceException("Pokemon data could not be loaded from PokeAPI.", exception);
        } catch (ResourceAccessException exception) {
            throw new ExternalServiceException("PokeAPI is currently unavailable. Please try again later.", exception);
        }
    }
}

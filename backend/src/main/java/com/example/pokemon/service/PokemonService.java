package com.example.pokemon.service;

import com.example.pokemon.client.PokeApiClient;
import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.mapper.PokemonMapper;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Service responsible for single-Pokemon lookup orchestration.
 *
 * <p>This service normalizes user input, calls the external PokeAPI client,
 * and maps the raw upstream payload into the normalized API response DTO.</p>
 */
@Service
public class PokemonService {

    static final String BLANK_POKEMON_NAME_MESSAGE = "Pokemon name must not be blank.";

    private final PokeApiClient pokeApiClient;
    private final PokemonMapper pokemonMapper;

    /**
     * Creates a new Pokemon service.
     *
     * @param pokeApiClient client used to fetch raw Pokemon data from PokeAPI
     * @param pokemonMapper mapper used to convert raw upstream models into API DTOs
     */
    public PokemonService(PokeApiClient pokeApiClient, PokemonMapper pokemonMapper) {
        this.pokeApiClient = pokeApiClient;
        this.pokemonMapper = pokemonMapper;
    }

    /**
     * Fetches a Pokemon by name and returns normalized details.
     *
     * @param name user-supplied Pokemon name, which may include mixed case or whitespace
     * @return normalized Pokemon details for the requested Pokemon
     * @throws IllegalArgumentException if the name is null or blank
     * @throws com.example.pokemon.exception.PokemonNotFoundException if the Pokemon is not found
     * @throws com.example.pokemon.exception.ExternalServiceException if PokeAPI cannot provide the data
     */
    public PokemonDetailsResponse getPokemonByName(String name) {
        String normalizedName = normalizePokemonName(name);
        return pokemonMapper.toPokemonResponse(
                pokeApiClient.getPokemonByName(normalizedName)
        );
    }

    /**
     * Normalizes a Pokemon name for consistent downstream lookup.
     *
     * @param name raw name value from a request
     * @return the trimmed, lowercase Pokemon name
     * @throws IllegalArgumentException if the supplied name is null or blank
     */
    String normalizePokemonName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(BLANK_POKEMON_NAME_MESSAGE);
        }

        String normalizedName = name.trim().toLowerCase(Locale.ROOT);

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException(BLANK_POKEMON_NAME_MESSAGE);
        }

        return normalizedName;
    }
}

package com.example.pokemon.service;

import com.example.pokemon.client.PokeApiClient;
import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.mapper.PokemonMapper;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PokemonService {

    static final String BLANK_POKEMON_NAME_MESSAGE = "Pokemon name must not be blank.";

    private final PokeApiClient pokeApiClient;
    private final PokemonMapper pokemonMapper;

    public PokemonService(PokeApiClient pokeApiClient, PokemonMapper pokemonMapper) {
        this.pokeApiClient = pokeApiClient;
        this.pokemonMapper = pokemonMapper;
    }

    public PokemonDetailsResponse getPokemonByName(String name) {
        String normalizedName = normalizePokemonName(name);
        return pokemonMapper.toPokemonResponse(
                pokeApiClient.getPokemonByName(normalizedName)
        );
    }

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

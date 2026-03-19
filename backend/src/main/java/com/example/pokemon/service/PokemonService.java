package com.example.pokemon.service;

import com.example.pokemon.client.PokeApiClient;
import com.example.pokemon.dto.PokemonResponse;
import com.example.pokemon.mapper.PokemonMapper;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PokemonService {

    private final PokeApiClient pokeApiClient;
    private final PokemonMapper pokemonMapper;

    public PokemonService(PokeApiClient pokeApiClient, PokemonMapper pokemonMapper) {
        this.pokeApiClient = pokeApiClient;
        this.pokemonMapper = pokemonMapper;
    }

    public PokemonResponse getPokemonByName(String name) {
        String normalizedName = normalizeName(name);
        return pokemonMapper.toPokemonResponse(
                pokeApiClient.getPokemonByName(normalizedName)
        );
    }

    private String normalizeName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Pokemon name must not be blank.");
        }

        String normalizedName = name.trim().toLowerCase(Locale.ROOT);

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Pokemon name must not be blank.");
        }

        return normalizedName;
    }
}

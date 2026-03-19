package com.example.pokemon.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokeApiPokemonResponse(
        int id,
        String name,
        List<TypeSlot> types,
        List<AbilitySlot> abilities,
        List<StatSlot> stats,
        Sprites sprites
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TypeSlot(int slot, NamedApiResource type) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AbilitySlot(NamedApiResource ability) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StatSlot(int base_stat, NamedApiResource stat) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Sprites(String front_default) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NamedApiResource(String name) {
    }
}

package com.example.pokemon.dto;

import java.util.List;

public record PokemonResponse(
        int id,
        String name,
        List<String> types,
        List<String> abilities,
        PokemonStatsResponse stats,
        String spriteUrl
) {
}

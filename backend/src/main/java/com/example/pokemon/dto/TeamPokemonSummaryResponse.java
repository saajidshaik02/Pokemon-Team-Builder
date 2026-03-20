package com.example.pokemon.dto;

import java.util.List;

public record TeamPokemonSummaryResponse(
        String name,
        List<String> types
) {
}

package com.example.pokemon.dto;

import java.util.List;

/**
 * Lightweight team-member summary included in the team analysis response.
 *
 * @param name normalized Pokemon name
 * @param types ordered Pokemon types
 */
public record TeamPokemonSummaryResponse(
        String name,
        List<String> types
) {
}

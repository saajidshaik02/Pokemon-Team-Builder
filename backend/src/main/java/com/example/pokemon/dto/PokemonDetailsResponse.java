package com.example.pokemon.dto;

import java.util.List;

/**
 * Normalized Pokemon details returned by the lookup endpoint.
 *
 * @param id Pokemon id
 * @param name normalized Pokemon name
 * @param types ordered Pokemon types
 * @param abilities Pokemon abilities
 * @param stats normalized base stats
 * @param spriteUrl sprite URL exposed by the API
 */
public record PokemonDetailsResponse(
        int id,
        String name,
        List<String> types,
        List<String> abilities,
        PokemonStatsResponse stats,
        String spriteUrl
) {
}

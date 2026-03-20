package com.example.pokemon.dto;

/**
 * Normalized Pokemon base stats.
 *
 * @param hp HP stat
 * @param attack Attack stat
 * @param defense Defense stat
 * @param specialAttack Special Attack stat
 * @param specialDefense Special Defense stat
 * @param speed Speed stat
 */
public record PokemonStatsResponse(
        int hp,
        int attack,
        int defense,
        int specialAttack,
        int specialDefense,
        int speed
) {
}

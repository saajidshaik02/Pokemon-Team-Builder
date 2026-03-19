package com.example.pokemon.dto;

public record PokemonStatsResponse(
        int hp,
        int attack,
        int defense,
        int specialAttack,
        int specialDefense,
        int speed
) {
}

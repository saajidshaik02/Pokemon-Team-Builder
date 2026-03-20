package com.example.pokemon.dto;

import java.util.List;

/**
 * Weakness entry describing the team's exposure to a specific attacking type.
 *
 * @param type attacking type that threatens the team
 * @param affectedPokemon Pokemon weak to that type
 * @param coveringPokemon teammates that resist or are immune to that type
 * @param severity explainable severity label for the weakness
 */
public record WeaknessResponse(
        String type,
        List<String> affectedPokemon,
        List<String> coveringPokemon,
        String severity
) {
}

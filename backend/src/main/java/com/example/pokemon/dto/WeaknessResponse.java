package com.example.pokemon.dto;

import java.util.List;

public record WeaknessResponse(
        String type,
        List<String> affectedPokemon,
        List<String> coveringPokemon,
        String severity
) {
}

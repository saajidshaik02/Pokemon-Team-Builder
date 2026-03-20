package com.example.pokemon.dto;

import java.util.List;

/**
 * Coverage entry describing which Pokemon resist or are immune to a type.
 *
 * @param type attacking type being described
 * @param pokemon Pokemon that provide the listed coverage
 */
public record TypeCoverageResponse(
        String type,
        List<String> pokemon
) {
}

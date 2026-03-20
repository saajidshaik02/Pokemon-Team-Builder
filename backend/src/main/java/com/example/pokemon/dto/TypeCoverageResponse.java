package com.example.pokemon.dto;

import java.util.List;

public record TypeCoverageResponse(
        String type,
        List<String> pokemon
) {
}

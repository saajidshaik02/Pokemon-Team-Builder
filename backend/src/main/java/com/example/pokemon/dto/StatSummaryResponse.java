package com.example.pokemon.dto;

import java.util.List;

public record StatSummaryResponse(
        int totalHp,
        int totalAttack,
        int totalDefense,
        int totalSpecialAttack,
        int totalSpecialDefense,
        int totalSpeed,
        int averageHp,
        int averageAttack,
        int averageDefense,
        int averageSpecialAttack,
        int averageSpecialDefense,
        int averageSpeed,
        List<String> strengths,
        List<String> weaknesses
) {
}

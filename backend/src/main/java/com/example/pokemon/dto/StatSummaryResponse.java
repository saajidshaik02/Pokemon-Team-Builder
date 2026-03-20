package com.example.pokemon.dto;

import java.util.List;

/**
 * Team-wide stat summary payload.
 *
 * @param totalHp total HP across the team
 * @param totalAttack total Attack across the team
 * @param totalDefense total Defense across the team
 * @param totalSpecialAttack total Special Attack across the team
 * @param totalSpecialDefense total Special Defense across the team
 * @param totalSpeed total Speed across the team
 * @param averageHp rounded average HP across the team
 * @param averageAttack rounded average Attack across the team
 * @param averageDefense rounded average Defense across the team
 * @param averageSpecialAttack rounded average Special Attack across the team
 * @param averageSpecialDefense rounded average Special Defense across the team
 * @param averageSpeed rounded average Speed across the team
 * @param strengths stat-based strengths inferred from the averages
 * @param weaknesses stat-based weaknesses inferred from the averages
 */
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

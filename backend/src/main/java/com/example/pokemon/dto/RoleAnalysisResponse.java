package com.example.pokemon.dto;

import java.util.List;
import java.util.Map;

/**
 * Team role analysis payload.
 *
 * @param roles mapping of Pokemon name to heuristic role label
 * @param roleCounts aggregate counts by role label
 * @param summary short human-readable observations about the team's role balance
 */
public record RoleAnalysisResponse(
        Map<String, String> roles,
        Map<String, Integer> roleCounts,
        List<String> summary
) {
}

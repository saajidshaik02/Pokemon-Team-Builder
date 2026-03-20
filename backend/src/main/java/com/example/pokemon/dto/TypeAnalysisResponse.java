package com.example.pokemon.dto;

import java.util.List;

/**
 * Team matchup analysis payload.
 *
 * @param weaknesses attacking types that threaten one or more team members
 * @param resistances attacking types the team resists
 * @param immunities attacking types the team can ignore completely
 * @param synergyNotes short explanatory notes about coverage and strengths
 */
public record TypeAnalysisResponse(
        List<WeaknessResponse> weaknesses,
        List<TypeCoverageResponse> resistances,
        List<TypeCoverageResponse> immunities,
        List<String> synergyNotes
) {
}

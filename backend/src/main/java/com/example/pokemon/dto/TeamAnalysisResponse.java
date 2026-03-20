package com.example.pokemon.dto;

import java.util.List;

/**
 * Full response DTO returned by the team analysis endpoint.
 *
 * @param team lightweight team summary
 * @param typeAnalysis team matchup analysis
 * @param roleAnalysis team role analysis
 * @param statSummary team stat summary
 * @param recommendations user-facing recommendations derived from the analyses
 */
public record TeamAnalysisResponse(
        List<TeamPokemonSummaryResponse> team,
        TypeAnalysisResponse typeAnalysis,
        RoleAnalysisResponse roleAnalysis,
        StatSummaryResponse statSummary,
        List<String> recommendations
) {
}

package com.example.pokemon.dto;

import java.util.List;

public record TeamAnalysisResponse(
        List<TeamPokemonSummaryResponse> team,
        TypeAnalysisResponse typeAnalysis,
        RoleAnalysisResponse roleAnalysis,
        StatSummaryResponse statSummary,
        List<String> recommendations
) {
}

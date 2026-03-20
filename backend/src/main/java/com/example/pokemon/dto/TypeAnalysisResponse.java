package com.example.pokemon.dto;

import java.util.List;

public record TypeAnalysisResponse(
        List<WeaknessResponse> weaknesses,
        List<TypeCoverageResponse> resistances,
        List<TypeCoverageResponse> immunities,
        List<String> synergyNotes
) {
}

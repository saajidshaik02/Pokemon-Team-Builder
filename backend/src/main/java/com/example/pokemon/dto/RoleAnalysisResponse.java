package com.example.pokemon.dto;

import java.util.List;
import java.util.Map;

public record RoleAnalysisResponse(
        Map<String, String> roles,
        Map<String, Integer> roleCounts,
        List<String> summary
) {
}

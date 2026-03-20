package com.example.pokemon.dto;

import java.util.List;

/**
 * Request DTO for team analysis.
 *
 * @param pokemonNames list of Pokemon names to analyze
 */
public record TeamAnalysisRequest(List<String> pokemonNames) {
}

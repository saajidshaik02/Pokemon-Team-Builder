package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.RoleAnalysisResponse;
import com.example.pokemon.dto.StatSummaryResponse;
import com.example.pokemon.dto.TeamAnalysisRequest;
import com.example.pokemon.dto.TeamAnalysisResponse;
import com.example.pokemon.dto.TeamPokemonSummaryResponse;
import com.example.pokemon.dto.TypeAnalysisResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;

@Service
public class TeamAnalysisService {

    private final PokemonService pokemonService;
    private final TeamTypeAnalysisService teamTypeAnalysisService;
    private final TeamRoleAnalysisService teamRoleAnalysisService;
    private final TeamStatSummaryService teamStatSummaryService;
    private final TeamRecommendationService teamRecommendationService;

    public TeamAnalysisService(
            PokemonService pokemonService,
            TeamTypeAnalysisService teamTypeAnalysisService,
            TeamRoleAnalysisService teamRoleAnalysisService,
            TeamStatSummaryService teamStatSummaryService,
            TeamRecommendationService teamRecommendationService
    ) {
        this.pokemonService = pokemonService;
        this.teamTypeAnalysisService = teamTypeAnalysisService;
        this.teamRoleAnalysisService = teamRoleAnalysisService;
        this.teamStatSummaryService = teamStatSummaryService;
        this.teamRecommendationService = teamRecommendationService;
    }

    public TeamAnalysisResponse analyzeTeam(TeamAnalysisRequest request) {
        List<String> normalizedNames = validateAndNormalize(request);
        List<PokemonDetailsResponse> team = normalizedNames.stream()
                .map(pokemonService::getPokemonByName)
                .toList();

        TypeAnalysisResponse typeAnalysis = teamTypeAnalysisService.analyze(team);
        RoleAnalysisResponse roleAnalysis = teamRoleAnalysisService.analyze(team);
        StatSummaryResponse statSummary = teamStatSummaryService.summarize(team);

        return new TeamAnalysisResponse(
                mapTeam(team),
                typeAnalysis,
                roleAnalysis,
                statSummary,
                teamRecommendationService.generate(typeAnalysis, roleAnalysis, statSummary)
        );
    }

    private List<String> validateAndNormalize(TeamAnalysisRequest request) {
        if (request == null || request.pokemonNames() == null) {
            throw new IllegalArgumentException("Pokemon team must contain between 1 and 6 Pokemon.");
        }

        List<String> pokemonNames = request.pokemonNames();
        if (pokemonNames.isEmpty() || pokemonNames.size() > 6) {
            throw new IllegalArgumentException("Pokemon team must contain between 1 and 6 Pokemon.");
        }

        List<String> normalizedNames = pokemonNames.stream()
                .map(pokemonService::normalizePokemonName)
                .toList();

        LinkedHashSet<String> uniqueNames = new LinkedHashSet<>(normalizedNames);
        if (uniqueNames.size() != normalizedNames.size()) {
            throw new IllegalArgumentException("Duplicate Pokemon are not allowed in the first version.");
        }

        return normalizedNames;
    }

    private List<TeamPokemonSummaryResponse> mapTeam(List<PokemonDetailsResponse> team) {
        return team.stream()
                .map(pokemon -> new TeamPokemonSummaryResponse(pokemon.name(), pokemon.types()))
                .toList();
    }
}

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

/**
 * Service that coordinates the full team analysis workflow.
 *
 * <p>This service validates the request, loads each Pokemon through the existing
 * lookup flow, delegates analysis to focused services, and assembles the final
 * team response DTO.</p>
 */
@Service
public class TeamAnalysisService {

    private final PokemonService pokemonService;
    private final TeamTypeAnalysisService teamTypeAnalysisService;
    private final TeamRoleAnalysisService teamRoleAnalysisService;
    private final TeamStatSummaryService teamStatSummaryService;
    private final TeamRecommendationService teamRecommendationService;

    /**
     * Creates a new team analysis service.
     *
     * @param pokemonService service used to resolve and normalize each Pokemon
     * @param teamTypeAnalysisService service used to aggregate weaknesses, resistances, and immunities
     * @param teamRoleAnalysisService service used to classify team roles
     * @param teamStatSummaryService service used to summarize team stats
     * @param teamRecommendationService service used to generate recommendations
     */
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

    /**
     * Analyzes a submitted Pokemon team.
     *
     * @param request request payload containing a team of Pokemon names
     * @return the complete team analysis response
     * @throws IllegalArgumentException if the team payload is missing, empty, too large, or contains duplicates
     * @throws com.example.pokemon.exception.PokemonNotFoundException if any Pokemon cannot be found
     * @throws com.example.pokemon.exception.ExternalServiceException if upstream data cannot be loaded
     */
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

    /**
     * Validates the submitted team request and normalizes each Pokemon name.
     *
     * @param request raw team analysis request
     * @return normalized Pokemon names in submission order
     * @throws IllegalArgumentException if the request is missing, outside the 1-6 size limit, or contains duplicates
     */
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

    /**
     * Converts fully detailed Pokemon DTOs into the lightweight team summary DTO used
     * by the team analysis response.
     *
     * @param team fully resolved team members
     * @return a summary view containing only Pokemon names and types
     */
    private List<TeamPokemonSummaryResponse> mapTeam(List<PokemonDetailsResponse> team) {
        return team.stream()
                .map(pokemon -> new TeamPokemonSummaryResponse(pokemon.name(), pokemon.types()))
                .toList();
    }
}

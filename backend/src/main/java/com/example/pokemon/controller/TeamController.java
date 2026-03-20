package com.example.pokemon.controller;

import com.example.pokemon.dto.TeamAnalysisRequest;
import com.example.pokemon.dto.TeamAnalysisResponse;
import com.example.pokemon.service.TeamAnalysisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for team analysis requests.
 *
 * <p>This controller exposes {@code POST /api/team/analyze} and accepts a list of
 * Pokemon names to analyze for matchups, roles, stat balance, and recommendations.</p>
 */
@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamAnalysisService teamAnalysisService;

    /**
     * Creates a new team analysis controller.
     *
     * @param teamAnalysisService service that coordinates team analysis
     */
    public TeamController(TeamAnalysisService teamAnalysisService) {
        this.teamAnalysisService = teamAnalysisService;
    }

    /**
     * Analyzes a submitted Pokemon team.
     *
     * @param request request payload containing the Pokemon names to analyze
     * @return structured team analysis covering matchups, roles, stats, and recommendations
     * @throws IllegalArgumentException if the request payload is invalid
     * @throws com.example.pokemon.exception.PokemonNotFoundException if any Pokemon name is invalid
     * @throws com.example.pokemon.exception.ExternalServiceException if upstream data cannot be loaded
     */
    @PostMapping("/analyze")
    public TeamAnalysisResponse analyzeTeam(@RequestBody TeamAnalysisRequest request) {
        return teamAnalysisService.analyzeTeam(request);
    }
}

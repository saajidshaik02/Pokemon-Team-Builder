package com.example.pokemon.controller;

import com.example.pokemon.dto.TeamAnalysisRequest;
import com.example.pokemon.dto.TeamAnalysisResponse;
import com.example.pokemon.service.TeamAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
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
@Tag(name = "Team Analysis", description = "Team submission and analysis endpoints")
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
    @Operation(
            summary = "Analyze a Pokemon team",
            description = "Validates a team of 1 to 6 Pokemon, loads each member through the backend lookup flow, and returns type, role, stat, and recommendation analysis."
    )
    @ApiResponse(responseCode = "200", description = "Team analysis completed")
    @ApiResponse(responseCode = "400", description = "Team payload was malformed, blank, duplicated, or outside the 1 to 6 size limit")
    @ApiResponse(responseCode = "404", description = "At least one Pokemon in the submitted team was not found")
    @ApiResponse(responseCode = "502", description = "PokeAPI could not be reached")
    public TeamAnalysisResponse analyzeTeam(
            @RequestBody(
                    description = "Ordered team payload. Duplicate names are currently rejected.",
                    required = true,
                    content = @Content
            )
            @org.springframework.web.bind.annotation.RequestBody TeamAnalysisRequest request
    ) {
        return teamAnalysisService.analyzeTeam(request);
    }
}

package com.example.pokemon.controller;

import com.example.pokemon.dto.TeamAnalysisRequest;
import com.example.pokemon.dto.TeamAnalysisResponse;
import com.example.pokemon.service.TeamAnalysisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamAnalysisService teamAnalysisService;

    public TeamController(TeamAnalysisService teamAnalysisService) {
        this.teamAnalysisService = teamAnalysisService;
    }

    @PostMapping("/analyze")
    public TeamAnalysisResponse analyzeTeam(@RequestBody TeamAnalysisRequest request) {
        return teamAnalysisService.analyzeTeam(request);
    }
}

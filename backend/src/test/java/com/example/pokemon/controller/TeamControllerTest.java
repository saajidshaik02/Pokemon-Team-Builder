package com.example.pokemon.controller;

import com.example.pokemon.dto.RoleAnalysisResponse;
import com.example.pokemon.dto.StatSummaryResponse;
import com.example.pokemon.dto.TeamAnalysisResponse;
import com.example.pokemon.dto.TeamPokemonSummaryResponse;
import com.example.pokemon.dto.TypeAnalysisResponse;
import com.example.pokemon.exception.GlobalExceptionHandler;
import com.example.pokemon.service.TeamAnalysisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
@Import(GlobalExceptionHandler.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamAnalysisService teamAnalysisService;

    @Test
    void shouldReturnTeamAnalysis() throws Exception {
        TeamAnalysisResponse response = new TeamAnalysisResponse(
                List.of(
                        new TeamPokemonSummaryResponse("pikachu", List.of("electric")),
                        new TeamPokemonSummaryResponse("blastoise", List.of("water"))
                ),
                new TypeAnalysisResponse(List.of(), List.of(), List.of(), List.of("team is comfortable into water attacks")),
                new RoleAnalysisResponse(Map.of("pikachu", "fast attacker"), Map.of("fast attacker", 1), List.of("team has at least one fast attacker")),
                new StatSummaryResponse(
                        114, 138, 90, 135, 114, 168,
                        57, 69, 45, 68, 57, 84,
                        List.of(),
                        List.of("team is light on physical defense")
                ),
                List.of("Consider adding a bulky support or defensive wall for better defensive balance.")
        );

        given(teamAnalysisService.analyzeTeam(any())).willReturn(response);

        mockMvc.perform(post("/api/team/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pokemonNames\":[\"pikachu\",\"blastoise\"]}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.team[0].name").value("pikachu"))
                .andExpect(jsonPath("$.roleAnalysis.roles.pikachu").value("fast attacker"))
                .andExpect(jsonPath("$.statSummary.averageSpeed").value(84))
                .andExpect(jsonPath("$.recommendations[0]").value("Consider adding a bulky support or defensive wall for better defensive balance."));
    }

    @Test
    void shouldReturnClearErrorForInvalidJson() throws Exception {
        mockMvc.perform(post("/api/team/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid-json}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Request body must be valid JSON."))
                .andExpect(jsonPath("$.path").value("/api/team/analyze"));
    }
}

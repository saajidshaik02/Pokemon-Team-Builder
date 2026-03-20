package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.dto.RoleAnalysisResponse;
import com.example.pokemon.dto.StatSummaryResponse;
import com.example.pokemon.dto.TeamAnalysisRequest;
import com.example.pokemon.dto.TeamAnalysisResponse;
import com.example.pokemon.dto.TypeAnalysisResponse;
import com.example.pokemon.dto.TypeCoverageResponse;
import com.example.pokemon.dto.WeaknessResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamAnalysisServiceTest {

    @Mock
    private PokemonService pokemonService;

    @Mock
    private TeamTypeAnalysisService teamTypeAnalysisService;

    @Mock
    private TeamRoleAnalysisService teamRoleAnalysisService;

    @Mock
    private TeamStatSummaryService teamStatSummaryService;

    @Mock
    private TeamRecommendationService teamRecommendationService;

    @InjectMocks
    private TeamAnalysisService teamAnalysisService;

    @Test
    void shouldRejectEmptyTeam() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teamAnalysisService.analyzeTeam(new TeamAnalysisRequest(List.of()))
        );

        assertEquals("Pokemon team must contain between 1 and 6 Pokemon.", exception.getMessage());
    }

    @Test
    void shouldRejectDuplicatePokemon() {
        given(pokemonService.normalizePokemonName("Pikachu")).willReturn("pikachu");
        given(pokemonService.normalizePokemonName("pikachu")).willReturn("pikachu");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teamAnalysisService.analyzeTeam(new TeamAnalysisRequest(List.of("Pikachu", "pikachu")))
        );

        assertEquals("Duplicate Pokemon are not allowed in the first version.", exception.getMessage());
    }

    @Test
    void shouldBuildTeamAnalysisAndRecommendations() {
        PokemonDetailsResponse charizard = pokemon("charizard", List.of("fire", "flying"), 84, 109, 100, 78, 78, 85);
        PokemonDetailsResponse gyarados = pokemon("gyarados", List.of("water", "flying"), 125, 60, 100, 95, 79, 81);
        PokemonDetailsResponse articuno = pokemon("articuno", List.of("ice", "flying"), 85, 95, 85, 90, 100, 125);

        TypeAnalysisResponse typeAnalysis = new TypeAnalysisResponse(
                List.of(new WeaknessResponse("rock", List.of("charizard", "gyarados", "articuno"), List.of(), "major weakness")),
                List.of(new TypeCoverageResponse("grass", List.of("charizard", "articuno"))),
                List.of(new TypeCoverageResponse("ground", List.of("charizard", "gyarados", "articuno"))),
                List.of("team is comfortable into ground attacks")
        );
        RoleAnalysisResponse roleAnalysis = new RoleAnalysisResponse(
                Map.of(
                        "charizard", "special attacker",
                        "gyarados", "physical attacker",
                        "articuno", "special attacker"
                ),
                Map.of(
                        "physical attacker", 1,
                        "special attacker", 2
                ),
                List.of(
                        "team lacks a clear fast attacker",
                        "team has limited defensive depth",
                        "team has some offensive variety"
                )
        );
        StatSummaryResponse statSummary = new StatSummaryResponse(
                263,
                294,
                257,
                264,
                291,
                285,
                88,
                98,
                86,
                88,
                97,
                95,
                List.of("team has strong average speed", "team has balanced defensive stats"),
                List.of()
        );

        given(pokemonService.normalizePokemonName("Charizard")).willReturn("charizard");
        given(pokemonService.normalizePokemonName("Gyarados")).willReturn("gyarados");
        given(pokemonService.normalizePokemonName("Articuno")).willReturn("articuno");
        given(pokemonService.getPokemonByName("charizard")).willReturn(charizard);
        given(pokemonService.getPokemonByName("gyarados")).willReturn(gyarados);
        given(pokemonService.getPokemonByName("articuno")).willReturn(articuno);
        given(teamTypeAnalysisService.analyze(List.of(charizard, gyarados, articuno))).willReturn(typeAnalysis);
        given(teamRoleAnalysisService.analyze(List.of(charizard, gyarados, articuno))).willReturn(roleAnalysis);
        given(teamStatSummaryService.summarize(List.of(charizard, gyarados, articuno))).willReturn(statSummary);
        given(teamRecommendationService.generate(typeAnalysis, roleAnalysis, statSummary)).willReturn(
                List.of(
                        "Team has a major weakness to rock attacks. Consider adding another rock resistance or immunity.",
                        "Consider adding a faster Pokemon to improve speed control.",
                        "Consider adding a bulky support or defensive wall for better defensive balance."
                )
        );

        TeamAnalysisResponse response = teamAnalysisService.analyzeTeam(
                new TeamAnalysisRequest(List.of("Charizard", "Gyarados", "Articuno"))
        );

        verify(pokemonService).getPokemonByName("charizard");
        verify(pokemonService).getPokemonByName("gyarados");
        verify(pokemonService).getPokemonByName("articuno");
        assertEquals(3, response.team().size());
        assertEquals("rock", response.typeAnalysis().weaknesses().getFirst().type());
        assertEquals(95, response.statSummary().averageSpeed());
        assertEquals(
                List.of(
                        "Team has a major weakness to rock attacks. Consider adding another rock resistance or immunity.",
                        "Consider adding a faster Pokemon to improve speed control.",
                        "Consider adding a bulky support or defensive wall for better defensive balance."
                ),
                response.recommendations()
        );
    }

    @Test
    void shouldAddStatBasedRecommendations() {
        PokemonDetailsResponse slowWall = pokemon("slowwall", List.of("water"), 60, 65, 40, 80, 70, 65);
        TypeAnalysisResponse typeAnalysis = new TypeAnalysisResponse(List.of(), List.of(), List.of(), List.of());
        RoleAnalysisResponse roleAnalysis = new RoleAnalysisResponse(
                Map.of("slowwall", "bulky support"),
                Map.of("bulky support", 1),
                List.of("team lacks a clear fast attacker")
        );
        StatSummaryResponse statSummary = new StatSummaryResponse(
                80, 60, 70, 65, 65, 40,
                80, 60, 70, 65, 65, 40,
                List.of(),
                List.of(
                        "team lacks average speed",
                        "team is light on physical defense",
                        "team is light on special defense",
                        "team lacks strong attacking stats"
                )
        );

        given(pokemonService.normalizePokemonName("Slowwall")).willReturn("slowwall");
        given(pokemonService.getPokemonByName("slowwall")).willReturn(slowWall);
        given(teamTypeAnalysisService.analyze(List.of(slowWall))).willReturn(typeAnalysis);
        given(teamRoleAnalysisService.analyze(List.of(slowWall))).willReturn(roleAnalysis);
        given(teamStatSummaryService.summarize(List.of(slowWall))).willReturn(statSummary);
        given(teamRecommendationService.generate(typeAnalysis, roleAnalysis, statSummary)).willReturn(
                List.of(
                        "Consider adding a faster Pokemon to improve speed control.",
                        "Consider adding a bulkier Pokemon to improve the team's overall durability.",
                        "Consider adding a stronger attacker to improve the team's offensive pressure."
                )
        );

        TeamAnalysisResponse response = teamAnalysisService.analyzeTeam(
                new TeamAnalysisRequest(List.of("Slowwall"))
        );

        assertEquals(
                List.of(
                        "Consider adding a faster Pokemon to improve speed control.",
                        "Consider adding a bulkier Pokemon to improve the team's overall durability.",
                        "Consider adding a stronger attacker to improve the team's offensive pressure."
                ),
                response.recommendations()
        );
    }

    @Test
    void shouldRejectTeamsLargerThanSixPokemon() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teamAnalysisService.analyzeTeam(
                        new TeamAnalysisRequest(List.of("a", "b", "c", "d", "e", "f", "g"))
                )
        );

        assertEquals("Pokemon team must contain between 1 and 6 Pokemon.", exception.getMessage());
    }

    private PokemonDetailsResponse pokemon(
            String name,
            List<String> types,
            int attack,
            int specialAttack,
            int speed,
            int hp,
            int defense,
            int specialDefense
    ) {
        return new PokemonDetailsResponse(
                1,
                name,
                types,
                List.of(),
                new PokemonStatsResponse(hp, attack, defense, specialAttack, specialDefense, speed),
                "https://example.test/" + name + "-artwork.png",
                "https://example.test/" + name + ".png"
        );
    }
}

package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.dto.TypeAnalysisResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeamTypeAnalysisServiceTest {

    private final TeamTypeAnalysisService teamTypeAnalysisService = new TeamTypeAnalysisService();

    @Test
    void shouldAggregateWeaknessesResistancesAndImmunities() {
        List<PokemonDetailsResponse> team = List.of(
                pokemon("pikachu", List.of("electric")),
                pokemon("charizard", List.of("fire", "flying")),
                pokemon("blastoise", List.of("water"))
        );

        TypeAnalysisResponse response = teamTypeAnalysisService.analyze(team);

        assertTrue(response.weaknesses().stream().anyMatch(weakness ->
                weakness.type().equals("electric")
                        && weakness.affectedPokemon().equals(List.of("charizard", "blastoise"))
                        && weakness.coveringPokemon().equals(List.of("pikachu"))
                        && weakness.severity().equals("shared weakness")
        ));
        assertTrue(response.immunities().stream().anyMatch(coverage ->
                coverage.type().equals("ground")
                        && coverage.pokemon().equals(List.of("charizard"))
        ));
        assertTrue(response.resistances().stream().anyMatch(coverage ->
                coverage.type().equals("electric")
                        && coverage.pokemon().equals(List.of("pikachu"))
        ));
        assertTrue(response.synergyNotes().stream().anyMatch(note -> note.contains("electric pressure can be covered by pikachu")));
        assertEquals("electric", response.weaknesses().getFirst().type());
    }

    private PokemonDetailsResponse pokemon(String name, List<String> types) {
        return new PokemonDetailsResponse(
                1,
                name,
                types,
                List.of(),
                new PokemonStatsResponse(50, 50, 50, 50, 50, 50),
                "https://example.test/" + name + "-artwork.png",
                "https://example.test/" + name + ".png"
        );
    }
}

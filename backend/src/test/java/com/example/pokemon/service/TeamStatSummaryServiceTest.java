package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.dto.StatSummaryResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeamStatSummaryServiceTest {

    private final TeamStatSummaryService teamStatSummaryService = new TeamStatSummaryService();

    @Test
    void shouldSummarizeTotalsAveragesStrengthsAndWeaknesses() {
        List<PokemonDetailsResponse> team = List.of(
                pokemon("alakazam", List.of("psychic"), 50, 135, 120, 55, 45, 95),
                pokemon("machamp", List.of("fighting"), 130, 65, 55, 90, 80, 85),
                pokemon("blissey", List.of("normal"), 10, 75, 55, 255, 10, 135)
        );

        StatSummaryResponse response = teamStatSummaryService.summarize(team);

        assertEquals(400, response.totalHp());
        assertEquals(77, response.averageSpeed());
        assertTrue(response.strengths().contains("team has solid overall HP"));
        assertTrue(response.weaknesses().contains("team lacks average speed"));
        assertTrue(response.weaknesses().contains("team is light on physical defense"));
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

package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.dto.RoleAnalysisResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeamRoleAnalysisServiceTest {

    private final TeamRoleAnalysisService teamRoleAnalysisService = new TeamRoleAnalysisService();

    @Test
    void shouldClassifyRolesAndDetectImbalance() {
        List<PokemonDetailsResponse> team = List.of(
                pokemon("alakazam", List.of("psychic"), 50, 135, 120, 55, 45, 95),
                pokemon("machamp", List.of("fighting"), 130, 65, 55, 90, 80, 85),
                pokemon("blissey", List.of("normal"), 10, 75, 55, 255, 10, 135)
        );

        RoleAnalysisResponse response = teamRoleAnalysisService.analyze(team);

        assertEquals("fast attacker", response.roles().get("alakazam"));
        assertEquals("physical attacker", response.roles().get("machamp"));
        assertEquals("defensive wall", response.roles().get("blissey"));
        assertTrue(response.summary().contains("team has at least one fast attacker"));
        assertTrue(response.summary().contains("team has some defensive backbone"));
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
                "https://example.test/" + name + ".png"
        );
    }
}

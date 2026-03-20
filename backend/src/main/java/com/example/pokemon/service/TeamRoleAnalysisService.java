package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.dto.RoleAnalysisResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamRoleAnalysisService {

    public RoleAnalysisResponse analyze(List<PokemonDetailsResponse> team) {
        Map<String, String> roles = new LinkedHashMap<>();
        Map<String, Integer> roleCounts = new LinkedHashMap<>();

        for (PokemonDetailsResponse pokemon : team) {
            String role = classifyRole(pokemon.stats());
            roles.put(pokemon.name(), role);
            roleCounts.merge(role, 1, Integer::sum);
        }

        return new RoleAnalysisResponse(roles, roleCounts, buildSummary(roleCounts, team.size()));
    }

    private String classifyRole(PokemonStatsResponse stats) {
        int attack = stats.attack();
        int specialAttack = stats.specialAttack();
        int speed = stats.speed();
        int defensiveBulk = stats.hp() + stats.defense() + stats.specialDefense();

        if (attack >= 95 && specialAttack >= 95) {
            return "mixed attacker";
        }

        if (defensiveBulk >= 300 && attack < 100 && specialAttack < 100) {
            return "defensive wall";
        }

        if (speed >= 100 && (attack >= 85 || specialAttack >= 85)) {
            return "fast attacker";
        }

        if (attack - specialAttack >= 20) {
            return "physical attacker";
        }

        if (specialAttack - attack >= 20) {
            return "special attacker";
        }

        return "bulky support";
    }

    private List<String> buildSummary(Map<String, Integer> roleCounts, int teamSize) {
        List<String> summary = new ArrayList<>();

        if (roleCounts.getOrDefault("fast attacker", 0) > 0) {
            summary.add("team has at least one fast attacker");
        } else {
            summary.add("team lacks a clear fast attacker");
        }

        if (roleCounts.getOrDefault("defensive wall", 0) + roleCounts.getOrDefault("bulky support", 0) > 0) {
            summary.add("team has some defensive backbone");
        } else {
            summary.add("team has limited defensive depth");
        }

        int physicalPressure = roleCounts.getOrDefault("physical attacker", 0);
        int specialPressure = roleCounts.getOrDefault("special attacker", 0);
        int mixedPressure = roleCounts.getOrDefault("mixed attacker", 0);

        if (physicalPressure >= Math.max(2, teamSize - 1) && specialPressure + mixedPressure == 0) {
            summary.add("team leans heavily physical");
        } else if (specialPressure >= Math.max(2, teamSize - 1) && physicalPressure + mixedPressure == 0) {
            summary.add("team leans heavily special");
        } else {
            summary.add("team has some offensive variety");
        }

        return summary;
    }
}

package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.StatSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamStatSummaryService {

    public StatSummaryResponse summarize(List<PokemonDetailsResponse> team) {
        int teamSize = team.size();
        int totalHp = team.stream().mapToInt(pokemon -> pokemon.stats().hp()).sum();
        int totalAttack = team.stream().mapToInt(pokemon -> pokemon.stats().attack()).sum();
        int totalDefense = team.stream().mapToInt(pokemon -> pokemon.stats().defense()).sum();
        int totalSpecialAttack = team.stream().mapToInt(pokemon -> pokemon.stats().specialAttack()).sum();
        int totalSpecialDefense = team.stream().mapToInt(pokemon -> pokemon.stats().specialDefense()).sum();
        int totalSpeed = team.stream().mapToInt(pokemon -> pokemon.stats().speed()).sum();

        int averageHp = average(totalHp, teamSize);
        int averageAttack = average(totalAttack, teamSize);
        int averageDefense = average(totalDefense, teamSize);
        int averageSpecialAttack = average(totalSpecialAttack, teamSize);
        int averageSpecialDefense = average(totalSpecialDefense, teamSize);
        int averageSpeed = average(totalSpeed, teamSize);

        return new StatSummaryResponse(
                totalHp,
                totalAttack,
                totalDefense,
                totalSpecialAttack,
                totalSpecialDefense,
                totalSpeed,
                averageHp,
                averageAttack,
                averageDefense,
                averageSpecialAttack,
                averageSpecialDefense,
                averageSpeed,
                buildStrengths(averageHp, averageAttack, averageDefense, averageSpecialAttack, averageSpecialDefense, averageSpeed),
                buildWeaknesses(averageHp, averageAttack, averageDefense, averageSpecialAttack, averageSpecialDefense, averageSpeed)
        );
    }

    private int average(int total, int teamSize) {
        return Math.round((float) total / teamSize);
    }

    private List<String> buildStrengths(
            int averageHp,
            int averageAttack,
            int averageDefense,
            int averageSpecialAttack,
            int averageSpecialDefense,
            int averageSpeed
    ) {
        List<String> strengths = new ArrayList<>();

        if (averageSpeed >= 90) {
            strengths.add("team has strong average speed");
        }

        if (averageAttack >= 95) {
            strengths.add("team has strong physical pressure");
        }

        if (averageSpecialAttack >= 95) {
            strengths.add("team has strong special pressure");
        }

        if (averageHp >= 90) {
            strengths.add("team has solid overall HP");
        }

        if (averageDefense >= 85 && averageSpecialDefense >= 85) {
            strengths.add("team has balanced defensive stats");
        }

        return strengths;
    }

    private List<String> buildWeaknesses(
            int averageHp,
            int averageAttack,
            int averageDefense,
            int averageSpecialAttack,
            int averageSpecialDefense,
            int averageSpeed
    ) {
        List<String> weaknesses = new ArrayList<>();

        if (averageSpeed < 80) {
            weaknesses.add("team lacks average speed");
        }

        if (averageDefense < 75) {
            weaknesses.add("team is light on physical defense");
        }

        if (averageSpecialDefense < 75) {
            weaknesses.add("team is light on special defense");
        }

        if (averageHp < 75) {
            weaknesses.add("team has low average HP");
        }

        if (averageAttack < 80 && averageSpecialAttack < 80) {
            weaknesses.add("team lacks strong attacking stats");
        }

        return weaknesses;
    }
}

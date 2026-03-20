package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.TypeAnalysisResponse;
import com.example.pokemon.dto.TypeCoverageResponse;
import com.example.pokemon.dto.WeaknessResponse;
import com.example.pokemon.model.TypeEffectivenessChart;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Service that aggregates team-wide type matchup information.
 *
 * <p>This service evaluates weaknesses, resistances, immunities, and simple synergy
 * notes by applying the static type-effectiveness chart to every team member.</p>
 */
@Service
public class TeamTypeAnalysisService {

    /**
     * Produces matchup analysis for a resolved Pokemon team.
     *
     * @param team normalized Pokemon details for the submitted team
     * @return structured type analysis including weaknesses, resistances, immunities, and synergy notes
     */
    public TypeAnalysisResponse analyze(List<PokemonDetailsResponse> team) {
        List<WeaknessResponse> weaknesses = new ArrayList<>();
        List<TypeCoverageResponse> resistances = new ArrayList<>();
        List<TypeCoverageResponse> immunities = new ArrayList<>();
        List<String> synergyNotes = new ArrayList<>();

        for (String attackType : TypeEffectivenessChart.attackTypes()) {
            List<String> weakPokemon = new ArrayList<>();
            List<String> resistingPokemon = new ArrayList<>();
            List<String> immunePokemon = new ArrayList<>();

            for (PokemonDetailsResponse pokemon : team) {
                double multiplier = TypeEffectivenessChart.multiplier(attackType, pokemon.types());

                if (multiplier == 0.0) {
                    immunePokemon.add(pokemon.name());
                } else if (multiplier > 1.0) {
                    weakPokemon.add(pokemon.name());
                } else if (multiplier < 1.0) {
                    resistingPokemon.add(pokemon.name());
                }
            }

            if (!weakPokemon.isEmpty()) {
                // Coverage Pokemon are teammates that either resist or completely ignore
                // the threatening attack type, making them natural pivots into that matchup.
                List<String> coveringPokemon = new ArrayList<>(new LinkedHashSet<>(resistingPokemon));
                coveringPokemon.addAll(
                        immunePokemon.stream()
                                .filter(name -> !coveringPokemon.contains(name))
                                .toList()
                );

                weaknesses.add(new WeaknessResponse(
                        attackType,
                        weakPokemon,
                        coveringPokemon,
                        determineSeverity(weakPokemon.size(), coveringPokemon.isEmpty())
                ));

                if (weakPokemon.size() >= 2 && !coveringPokemon.isEmpty()) {
                    synergyNotes.add(attackType + " pressure can be covered by " + String.join(", ", coveringPokemon));
                }
            }

            if (!resistingPokemon.isEmpty()) {
                resistances.add(new TypeCoverageResponse(attackType, resistingPokemon));
            }

            if (!immunePokemon.isEmpty()) {
                immunities.add(new TypeCoverageResponse(attackType, immunePokemon));
            }

            if (weakPokemon.isEmpty() && resistingPokemon.size() + immunePokemon.size() >= 2) {
                synergyNotes.add("team is comfortable into " + attackType + " attacks");
            }
        }

        weaknesses.sort(Comparator
                .comparingInt((WeaknessResponse weakness) -> weakness.affectedPokemon().size())
                .reversed()
                .thenComparing(WeaknessResponse::type));
        resistances.sort(Comparator.comparing(TypeCoverageResponse::type));
        immunities.sort(Comparator.comparing(TypeCoverageResponse::type));

        return new TypeAnalysisResponse(weaknesses, resistances, immunities, synergyNotes);
    }

    /**
     * Converts the number of affected teammates and available coverage into a simple
     * user-facing weakness severity label.
     *
     * @param weakCount number of Pokemon weak to the attacking type
     * @param noCoverage whether the team lacks any resist or immunity for the type
     * @return the severity label used in the type analysis response
     */
    private String determineSeverity(int weakCount, boolean noCoverage) {
        if (weakCount >= 3) {
            return "major weakness";
        }

        if (weakCount >= 2 && noCoverage) {
            return "coverage concern";
        }

        if (weakCount >= 2) {
            return "shared weakness";
        }

        return "isolated weakness";
    }
}

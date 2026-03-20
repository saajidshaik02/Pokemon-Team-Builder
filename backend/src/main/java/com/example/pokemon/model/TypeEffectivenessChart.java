package com.example.pokemon.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Static type-effectiveness lookup table used by team matchup analysis.
 *
 * <p>The chart stores attack-type to defense-type multipliers and exposes helpers
 * for iterating supported attack types and calculating combined multipliers for
 * dual-type Pokemon.</p>
 */
public final class TypeEffectivenessChart {

    private static final Map<String, Map<String, Double>> ATTACK_CHART = createChart();

    private TypeEffectivenessChart() {
    }

    /**
     * Returns all supported attacking types in deterministic iteration order.
     *
     * @return ordered list of attack types represented in the chart
     */
    public static List<String> attackTypes() {
        return List.copyOf(ATTACK_CHART.keySet());
    }

    /**
     * Calculates the full type-effectiveness multiplier for an attacking type
     * against one or more defending types.
     *
     * @param attackType attacking type being evaluated
     * @param defendingTypes defending Pokemon type list
     * @return combined type-effectiveness multiplier
     */
    public static double multiplier(String attackType, List<String> defendingTypes) {
        double multiplier = 1.0;

        for (String defendingType : defendingTypes) {
            multiplier *= ATTACK_CHART
                    .getOrDefault(attackType, Map.of())
                    .getOrDefault(defendingType, 1.0);
        }

        return multiplier;
    }

    /**
     * Creates the full static attack chart used for matchup analysis.
     *
     * @return immutable type-effectiveness chart keyed by attack type
     */
    private static Map<String, Map<String, Double>> createChart() {
        Map<String, Map<String, Double>> chart = new LinkedHashMap<>();
        chart.put("normal", effectiveness(entry("rock", 0.5), entry("ghost", 0.0), entry("steel", 0.5)));
        chart.put("fire", effectiveness(entry("fire", 0.5), entry("water", 0.5), entry("grass", 2.0), entry("ice", 2.0), entry("bug", 2.0), entry("rock", 0.5), entry("dragon", 0.5), entry("steel", 2.0)));
        chart.put("water", effectiveness(entry("fire", 2.0), entry("water", 0.5), entry("grass", 0.5), entry("ground", 2.0), entry("rock", 2.0), entry("dragon", 0.5)));
        chart.put("electric", effectiveness(entry("water", 2.0), entry("electric", 0.5), entry("grass", 0.5), entry("ground", 0.0), entry("flying", 2.0), entry("dragon", 0.5)));
        chart.put("grass", effectiveness(entry("fire", 0.5), entry("water", 2.0), entry("grass", 0.5), entry("poison", 0.5), entry("ground", 2.0), entry("flying", 0.5), entry("bug", 0.5), entry("rock", 2.0), entry("dragon", 0.5), entry("steel", 0.5)));
        chart.put("ice", effectiveness(entry("fire", 0.5), entry("water", 0.5), entry("grass", 2.0), entry("ground", 2.0), entry("flying", 2.0), entry("dragon", 2.0), entry("steel", 0.5), entry("ice", 0.5)));
        chart.put("fighting", effectiveness(entry("normal", 2.0), entry("ice", 2.0), entry("poison", 0.5), entry("flying", 0.5), entry("psychic", 0.5), entry("bug", 0.5), entry("rock", 2.0), entry("ghost", 0.0), entry("dark", 2.0), entry("steel", 2.0), entry("fairy", 0.5)));
        chart.put("poison", effectiveness(entry("grass", 2.0), entry("poison", 0.5), entry("ground", 0.5), entry("rock", 0.5), entry("ghost", 0.5), entry("steel", 0.0), entry("fairy", 2.0)));
        chart.put("ground", effectiveness(entry("fire", 2.0), entry("electric", 2.0), entry("grass", 0.5), entry("poison", 2.0), entry("flying", 0.0), entry("bug", 0.5), entry("rock", 2.0), entry("steel", 2.0)));
        chart.put("flying", effectiveness(entry("electric", 0.5), entry("grass", 2.0), entry("fighting", 2.0), entry("bug", 2.0), entry("rock", 0.5), entry("steel", 0.5)));
        chart.put("psychic", effectiveness(entry("fighting", 2.0), entry("poison", 2.0), entry("psychic", 0.5), entry("dark", 0.0), entry("steel", 0.5)));
        chart.put("bug", effectiveness(entry("fire", 0.5), entry("grass", 2.0), entry("fighting", 0.5), entry("poison", 0.5), entry("flying", 0.5), entry("psychic", 2.0), entry("ghost", 0.5), entry("dark", 2.0), entry("steel", 0.5), entry("fairy", 0.5)));
        chart.put("rock", effectiveness(entry("fire", 2.0), entry("ice", 2.0), entry("fighting", 0.5), entry("ground", 0.5), entry("flying", 2.0), entry("bug", 2.0), entry("steel", 0.5)));
        chart.put("ghost", effectiveness(entry("normal", 0.0), entry("psychic", 2.0), entry("ghost", 2.0), entry("dark", 0.5)));
        chart.put("dragon", effectiveness(entry("dragon", 2.0), entry("steel", 0.5), entry("fairy", 0.0)));
        chart.put("dark", effectiveness(entry("fighting", 0.5), entry("psychic", 2.0), entry("ghost", 2.0), entry("dark", 0.5), entry("fairy", 0.5)));
        chart.put("steel", effectiveness(entry("fire", 0.5), entry("water", 0.5), entry("electric", 0.5), entry("ice", 2.0), entry("rock", 2.0), entry("steel", 0.5), entry("fairy", 2.0)));
        chart.put("fairy", effectiveness(entry("fire", 0.5), entry("fighting", 2.0), entry("poison", 0.5), entry("dragon", 2.0), entry("dark", 2.0), entry("steel", 0.5)));
        return Map.copyOf(chart);
    }

    /**
     * Convenience helper for declaring type-effectiveness entries.
     *
     * @param entries defense-type to multiplier entries
     * @return immutable effectiveness map for a single attack type
     */
    @SafeVarargs
    private static Map<String, Double> effectiveness(Map.Entry<String, Double>... entries) {
        return Map.ofEntries(entries);
    }
}

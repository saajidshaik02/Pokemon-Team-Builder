package com.example.pokemon.service;

import com.example.pokemon.dto.RoleAnalysisResponse;
import com.example.pokemon.dto.StatSummaryResponse;
import com.example.pokemon.dto.TypeAnalysisResponse;
import com.example.pokemon.dto.WeaknessResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Service
public class TeamRecommendationService {

    public List<String> generate(
            TypeAnalysisResponse typeAnalysis,
            RoleAnalysisResponse roleAnalysis,
            StatSummaryResponse statSummary
    ) {
        LinkedHashSet<String> recommendations = new LinkedHashSet<>();

        for (WeaknessResponse weakness : typeAnalysis.weaknesses()) {
            if ("major weakness".equals(weakness.severity())) {
                recommendations.add(
                        "Team has a major weakness to " + weakness.type()
                                + " attacks. Consider adding another "
                                + weakness.type() + " resistance or immunity."
                );
            } else if ("coverage concern".equals(weakness.severity())) {
                recommendations.add(
                        "Team has limited answers to " + weakness.type()
                                + " attacks. Consider adding a safer defensive cover."
                );
            }
        }

        Map<String, Integer> roleCounts = roleAnalysis.roleCounts();
        int fastAttackers = roleCounts.getOrDefault("fast attacker", 0);
        int defensiveBackbone = roleCounts.getOrDefault("defensive wall", 0)
                + roleCounts.getOrDefault("bulky support", 0);
        int physicalPressure = roleCounts.getOrDefault("physical attacker", 0);
        int specialPressure = roleCounts.getOrDefault("special attacker", 0);
        int mixedPressure = roleCounts.getOrDefault("mixed attacker", 0);

        if (fastAttackers == 0 || statSummary.averageSpeed() < 80) {
            recommendations.add("Consider adding a faster Pokemon to improve speed control.");
        }

        if (defensiveBackbone == 0) {
            recommendations.add("Consider adding a bulky support or defensive wall for better defensive balance.");
        }

        if (physicalPressure >= 3 && specialPressure + mixedPressure == 0) {
            recommendations.add("Team leans too heavily physical. Consider adding a special attacker.");
        }

        if (specialPressure >= 3 && physicalPressure + mixedPressure == 0) {
            recommendations.add("Team leans too heavily special. Consider adding a physical attacker.");
        }

        if (statSummary.averageDefense() < 75 || statSummary.averageSpecialDefense() < 75 || statSummary.averageHp() < 75) {
            recommendations.add("Consider adding a bulkier Pokemon to improve the team's overall durability.");
        }

        if (statSummary.averageAttack() < 80 && statSummary.averageSpecialAttack() < 80) {
            recommendations.add("Consider adding a stronger attacker to improve the team's offensive pressure.");
        }

        return new ArrayList<>(recommendations);
    }
}

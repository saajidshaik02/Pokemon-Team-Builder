package com.example.pokemon.service;

import com.example.pokemon.dto.RoleAnalysisResponse;
import com.example.pokemon.dto.StatSummaryResponse;
import com.example.pokemon.dto.TypeAnalysisResponse;
import com.example.pokemon.dto.WeaknessResponse;
import com.example.pokemon.config.AnalysisProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Service that converts analysis outputs into user-facing recommendations.
 *
 * <p>The rules in this service are intentionally deterministic and easy to explain.
 * It combines matchup exposure, role balance, and stat summary thresholds into
 * concise next-step suggestions.</p>
 */
@Service
public class TeamRecommendationService {

    private final AnalysisProperties analysisProperties;

    public TeamRecommendationService(AnalysisProperties analysisProperties) {
        this.analysisProperties = analysisProperties;
    }

    /**
     * Generates recommendations for a team based on matchup, role, and stat analysis.
     *
     * @param typeAnalysis aggregated type matchup analysis for the team
     * @param roleAnalysis aggregated role analysis for the team
     * @param statSummary aggregate stat totals, averages, strengths, and weaknesses
     * @return ordered, deduplicated recommendation messages
     */
    public List<String> generate(
            TypeAnalysisResponse typeAnalysis,
            RoleAnalysisResponse roleAnalysis,
            StatSummaryResponse statSummary
    ) {
        AnalysisProperties.Role roleThresholds = analysisProperties.getRole();
        AnalysisProperties.StatSummary statThresholds = analysisProperties.getStatSummary();
        AnalysisProperties.Recommendation recommendationThresholds = analysisProperties.getRecommendation();
        // LinkedHashSet preserves insertion order while preventing repeated suggestions
        // when multiple rules point to the same high-level recommendation.
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

        if (fastAttackers < roleThresholds.getMinFastAttackers()
                || statSummary.averageSpeed() < statThresholds.getLowSpeedAverage()) {
            recommendations.add("Consider adding a faster Pokemon to improve speed control.");
        }

        if (defensiveBackbone < roleThresholds.getMinDefensiveBackbone()) {
            recommendations.add("Consider adding a bulky support or defensive wall for better defensive balance.");
        }

        if (physicalPressure >= recommendationThresholds.getRecommendedOffensiveOverloadCount()
                && specialPressure + mixedPressure == 0) {
            recommendations.add("Team leans too heavily physical. Consider adding a special attacker.");
        }

        if (specialPressure >= recommendationThresholds.getRecommendedOffensiveOverloadCount()
                && physicalPressure + mixedPressure == 0) {
            recommendations.add("Team leans too heavily special. Consider adding a physical attacker.");
        }

        if (statSummary.averageDefense() < statThresholds.getLowDefenseAverage()
                || statSummary.averageSpecialDefense() < statThresholds.getLowDefenseAverage()
                || statSummary.averageHp() < statThresholds.getLowDefenseAverage()) {
            recommendations.add("Consider adding a bulkier Pokemon to improve the team's overall durability.");
        }

        if (statSummary.averageAttack() < statThresholds.getLowAttackAverage()
                && statSummary.averageSpecialAttack() < statThresholds.getLowAttackAverage()) {
            recommendations.add("Consider adding a stronger attacker to improve the team's offensive pressure.");
        }

        return new ArrayList<>(recommendations);
    }
}

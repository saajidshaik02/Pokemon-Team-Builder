package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.dto.RoleAnalysisResponse;
import com.example.pokemon.config.AnalysisProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service that classifies Pokemon into simple heuristic team roles.
 *
 * <p>The role labels are intentionally lightweight and based only on base stats.
 * They are designed to support explainable team-balance output rather than
 * competitive-grade battle analysis.</p>
 */
@Service
public class TeamRoleAnalysisService {

    private final AnalysisProperties analysisProperties;

    public TeamRoleAnalysisService(AnalysisProperties analysisProperties) {
        this.analysisProperties = analysisProperties;
    }

    /**
     * Produces role analysis for a resolved team.
     *
     * @param team normalized Pokemon details for the submitted team
     * @return role assignments, role counts, and a short role-balance summary
     */
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

    /**
     * Classifies a single Pokemon into one of the supported heuristic roles.
     *
     * @param stats normalized base stats for the Pokemon
     * @return a simple role label such as {@code fast attacker} or {@code defensive wall}
     */
    private String classifyRole(PokemonStatsResponse stats) {
        AnalysisProperties.Role role = analysisProperties.getRole();
        int hp = stats.hp();
        int attack = stats.attack();
        int defense = stats.defense();
        int specialAttack = stats.specialAttack();
        int specialDefense = stats.specialDefense();
        int speed = stats.speed();
        int defensiveBulk = hp + defense + specialDefense;
        int offensiveTotal = attack + specialAttack;
        int totalStat = attack + specialAttack + speed + defensiveBulk;

        // Role classification uses how much each stat group contributes to the whole
        // six-stat profile instead of relying on raw flat values alone. That keeps low
        // total-stat Pokemon from defaulting into support roles simply for missing an
        // absolute cutoff while still producing deterministic, explainable labels.
        double bulkShare = share(defensiveBulk, totalStat);
        double offenseShare = share(offensiveTotal, totalStat);
        double speedShare = share(speed, totalStat);
        double attackSplitShare = share(attack, offensiveTotal);
        double specialAttackSplitShare = share(specialAttack, offensiveTotal);

        if (offenseShare >= role.getMixedAttackerMinOffenseShare()
                && attackSplitShare >= role.getMixedAttackerMinSplitShare()
                && specialAttackSplitShare >= role.getMixedAttackerMinSplitShare()
                && Math.abs(attackSplitShare - specialAttackSplitShare) <= role.getMixedAttackerMaxSplitGap()) {
            return "mixed attacker";
        }

        if (bulkShare >= role.getDefensiveWallMinBulkShare()
                && offenseShare <= role.getDefensiveWallMaxOffenseShare()) {
            return "defensive wall";
        }

        if (speedShare >= role.getFastAttackerMinSpeedShare()
                && offenseShare >= role.getFastAttackerMinOffenseShare()) {
            return "fast attacker";
        }

        if (offenseShare >= role.getAttackerMinOffenseShare()
                && attackSplitShare >= role.getDominantSplitShare()) {
            return "physical attacker";
        }

        if (offenseShare >= role.getAttackerMinOffenseShare()
                && specialAttackSplitShare >= role.getDominantSplitShare()) {
            return "special attacker";
        }

        if (offenseShare >= role.getBalancedAttackerMinOffenseShare()) {
            return attack >= specialAttack ? "physical attacker" : "special attacker";
        }

        if (bulkShare >= role.getBulkySupportMinBulkShare()) {
            return "bulky support";
        }

        return "bulky support";
    }

    /**
     * Builds human-readable summary statements for the team's role distribution.
     *
     * @param roleCounts aggregated role counts across the team
     * @param teamSize number of Pokemon on the team
     * @return concise summary messages describing role strengths or imbalances
     */
    private List<String> buildSummary(Map<String, Integer> roleCounts, int teamSize) {
        AnalysisProperties.Role role = analysisProperties.getRole();
        List<String> summary = new ArrayList<>();

        if (roleCounts.getOrDefault("fast attacker", 0) >= role.getMinFastAttackers()) {
            summary.add("team has at least one fast attacker");
        } else {
            summary.add("team lacks a clear fast attacker");
        }

        if (roleCounts.getOrDefault("defensive wall", 0) + roleCounts.getOrDefault("bulky support", 0)
                >= role.getMinDefensiveBackbone()) {
            summary.add("team has some defensive backbone");
        } else {
            summary.add("team has limited defensive depth");
        }

        int physicalPressure = roleCounts.getOrDefault("physical attacker", 0);
        int specialPressure = roleCounts.getOrDefault("special attacker", 0);
        int mixedPressure = roleCounts.getOrDefault("mixed attacker", 0);
        int requiredLeanCount = Math.max(
                role.getMinOffensiveLeanCount(),
                teamSize - role.getMaxOffRoleMembersForLean()
        );

        if (physicalPressure >= requiredLeanCount && specialPressure + mixedPressure == 0) {
            summary.add("team leans heavily physical");
        } else if (specialPressure >= requiredLeanCount && physicalPressure + mixedPressure == 0) {
            summary.add("team leans heavily special");
        } else {
            summary.add("team has some offensive variety");
        }

        return summary;
    }

    private double share(int value, int total) {
        if (total <= 0) {
            return 0.0;
        }
        return (double) value / total;
    }
}

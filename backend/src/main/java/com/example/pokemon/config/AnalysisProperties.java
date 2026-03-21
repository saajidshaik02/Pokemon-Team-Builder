package com.example.pokemon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Typed backend configuration for deterministic analysis heuristics.
 */
@Component
@ConfigurationProperties(prefix = "analysis")
public class AnalysisProperties {

    private final Type type = new Type();
    private final StatSummary statSummary = new StatSummary();
    private final Role role = new Role();
    private final Recommendation recommendation = new Recommendation();

    public Type getType() {
        return type;
    }

    public StatSummary getStatSummary() {
        return statSummary;
    }

    public Role getRole() {
        return role;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public static class Type {
        private int sharedWeaknessCount = 2;
        private int majorWeaknessCount = 3;
        private int comfortableCoverageCount = 2;

        public int getSharedWeaknessCount() {
            return sharedWeaknessCount;
        }

        public void setSharedWeaknessCount(int sharedWeaknessCount) {
            this.sharedWeaknessCount = sharedWeaknessCount;
        }

        public int getMajorWeaknessCount() {
            return majorWeaknessCount;
        }

        public void setMajorWeaknessCount(int majorWeaknessCount) {
            this.majorWeaknessCount = majorWeaknessCount;
        }

        public int getComfortableCoverageCount() {
            return comfortableCoverageCount;
        }

        public void setComfortableCoverageCount(int comfortableCoverageCount) {
            this.comfortableCoverageCount = comfortableCoverageCount;
        }
    }

    public static class StatSummary {
        private int strongSpeedAverage = 90;
        private int strongAttackAverage = 95;
        private int strongSpecialAttackAverage = 95;
        private int strongHpAverage = 90;
        private int strongDefenseAverage = 85;
        private int lowSpeedAverage = 80;
        private int lowDefenseAverage = 75;
        private int lowAttackAverage = 80;

        public int getStrongSpeedAverage() {
            return strongSpeedAverage;
        }

        public void setStrongSpeedAverage(int strongSpeedAverage) {
            this.strongSpeedAverage = strongSpeedAverage;
        }

        public int getStrongAttackAverage() {
            return strongAttackAverage;
        }

        public void setStrongAttackAverage(int strongAttackAverage) {
            this.strongAttackAverage = strongAttackAverage;
        }

        public int getStrongSpecialAttackAverage() {
            return strongSpecialAttackAverage;
        }

        public void setStrongSpecialAttackAverage(int strongSpecialAttackAverage) {
            this.strongSpecialAttackAverage = strongSpecialAttackAverage;
        }

        public int getStrongHpAverage() {
            return strongHpAverage;
        }

        public void setStrongHpAverage(int strongHpAverage) {
            this.strongHpAverage = strongHpAverage;
        }

        public int getStrongDefenseAverage() {
            return strongDefenseAverage;
        }

        public void setStrongDefenseAverage(int strongDefenseAverage) {
            this.strongDefenseAverage = strongDefenseAverage;
        }

        public int getLowSpeedAverage() {
            return lowSpeedAverage;
        }

        public void setLowSpeedAverage(int lowSpeedAverage) {
            this.lowSpeedAverage = lowSpeedAverage;
        }

        public int getLowDefenseAverage() {
            return lowDefenseAverage;
        }

        public void setLowDefenseAverage(int lowDefenseAverage) {
            this.lowDefenseAverage = lowDefenseAverage;
        }

        public int getLowAttackAverage() {
            return lowAttackAverage;
        }

        public void setLowAttackAverage(int lowAttackAverage) {
            this.lowAttackAverage = lowAttackAverage;
        }
    }

    public static class Role {
        private double fastAttackerMinSpeedShare = 0.19;
        private double fastAttackerMinOffenseShare = 0.30;
        private double mixedAttackerMinOffenseShare = 0.34;
        private double mixedAttackerMinSplitShare = 0.43;
        private double mixedAttackerMaxSplitGap = 0.12;
        private double defensiveWallMinBulkShare = 0.55;
        private double defensiveWallMaxOffenseShare = 0.32;
        private double attackerMinOffenseShare = 0.28;
        private double dominantSplitShare = 0.58;
        private double balancedAttackerMinOffenseShare = 0.33;
        private double bulkySupportMinBulkShare = 0.50;
        private int minOffensiveLeanCount = 2;
        private int maxOffRoleMembersForLean = 1;
        private int minFastAttackers = 1;
        private int minDefensiveBackbone = 1;

        public double getFastAttackerMinSpeedShare() {
            return fastAttackerMinSpeedShare;
        }

        public void setFastAttackerMinSpeedShare(double fastAttackerMinSpeedShare) {
            this.fastAttackerMinSpeedShare = fastAttackerMinSpeedShare;
        }

        public double getFastAttackerMinOffenseShare() {
            return fastAttackerMinOffenseShare;
        }

        public void setFastAttackerMinOffenseShare(double fastAttackerMinOffenseShare) {
            this.fastAttackerMinOffenseShare = fastAttackerMinOffenseShare;
        }

        public double getMixedAttackerMinOffenseShare() {
            return mixedAttackerMinOffenseShare;
        }

        public void setMixedAttackerMinOffenseShare(double mixedAttackerMinOffenseShare) {
            this.mixedAttackerMinOffenseShare = mixedAttackerMinOffenseShare;
        }

        public double getMixedAttackerMinSplitShare() {
            return mixedAttackerMinSplitShare;
        }

        public void setMixedAttackerMinSplitShare(double mixedAttackerMinSplitShare) {
            this.mixedAttackerMinSplitShare = mixedAttackerMinSplitShare;
        }

        public double getMixedAttackerMaxSplitGap() {
            return mixedAttackerMaxSplitGap;
        }

        public void setMixedAttackerMaxSplitGap(double mixedAttackerMaxSplitGap) {
            this.mixedAttackerMaxSplitGap = mixedAttackerMaxSplitGap;
        }

        public double getDefensiveWallMinBulkShare() {
            return defensiveWallMinBulkShare;
        }

        public void setDefensiveWallMinBulkShare(double defensiveWallMinBulkShare) {
            this.defensiveWallMinBulkShare = defensiveWallMinBulkShare;
        }

        public double getDefensiveWallMaxOffenseShare() {
            return defensiveWallMaxOffenseShare;
        }

        public void setDefensiveWallMaxOffenseShare(double defensiveWallMaxOffenseShare) {
            this.defensiveWallMaxOffenseShare = defensiveWallMaxOffenseShare;
        }

        public double getAttackerMinOffenseShare() {
            return attackerMinOffenseShare;
        }

        public void setAttackerMinOffenseShare(double attackerMinOffenseShare) {
            this.attackerMinOffenseShare = attackerMinOffenseShare;
        }

        public double getDominantSplitShare() {
            return dominantSplitShare;
        }

        public void setDominantSplitShare(double dominantSplitShare) {
            this.dominantSplitShare = dominantSplitShare;
        }

        public double getBalancedAttackerMinOffenseShare() {
            return balancedAttackerMinOffenseShare;
        }

        public void setBalancedAttackerMinOffenseShare(double balancedAttackerMinOffenseShare) {
            this.balancedAttackerMinOffenseShare = balancedAttackerMinOffenseShare;
        }

        public double getBulkySupportMinBulkShare() {
            return bulkySupportMinBulkShare;
        }

        public void setBulkySupportMinBulkShare(double bulkySupportMinBulkShare) {
            this.bulkySupportMinBulkShare = bulkySupportMinBulkShare;
        }

        public int getMinOffensiveLeanCount() {
            return minOffensiveLeanCount;
        }

        public void setMinOffensiveLeanCount(int minOffensiveLeanCount) {
            this.minOffensiveLeanCount = minOffensiveLeanCount;
        }

        public int getMaxOffRoleMembersForLean() {
            return maxOffRoleMembersForLean;
        }

        public void setMaxOffRoleMembersForLean(int maxOffRoleMembersForLean) {
            this.maxOffRoleMembersForLean = maxOffRoleMembersForLean;
        }

        public int getMinFastAttackers() {
            return minFastAttackers;
        }

        public void setMinFastAttackers(int minFastAttackers) {
            this.minFastAttackers = minFastAttackers;
        }

        public int getMinDefensiveBackbone() {
            return minDefensiveBackbone;
        }

        public void setMinDefensiveBackbone(int minDefensiveBackbone) {
            this.minDefensiveBackbone = minDefensiveBackbone;
        }
    }

    public static class Recommendation {
        private int recommendedOffensiveOverloadCount = 3;

        public int getRecommendedOffensiveOverloadCount() {
            return recommendedOffensiveOverloadCount;
        }

        public void setRecommendedOffensiveOverloadCount(int recommendedOffensiveOverloadCount) {
            this.recommendedOffensiveOverloadCount = recommendedOffensiveOverloadCount;
        }
    }
}

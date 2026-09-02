package com.vestige.engine;

import com.vestige.model.enums.DeviceCategoryType;
import com.vestige.model.enums.DeviceConditionGrade;
import com.vestige.model.enums.EngineConfidenceLevel;
import com.vestige.model.enums.EngineRecommendationType;
import com.vestige.model.enums.PartAvailabilityStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DecisionEngineService {

    public AssessmentResult evaluate(
            DeviceCategoryType category,
            int ageYears,
            DeviceConditionGrade condition,
            BigDecimal estimatedRepairCost,
            BigDecimal originalValue,
            PartAvailabilityStatus partAvailability,
            List<String> knownIssues
    ) {
        int score = 100;
        StringBuilder rationale = new StringBuilder();

        // 1. Economic Cost-to-Value Ratio Analysis
        BigDecimal cost = (estimatedRepairCost != null && estimatedRepairCost.compareTo(BigDecimal.ZERO) >= 0)
                ? estimatedRepairCost : BigDecimal.ZERO;
        BigDecimal value = (originalValue != null && originalValue.compareTo(BigDecimal.ZERO) > 0)
                ? originalValue : BigDecimal.valueOf(10000);

        BigDecimal ratio = cost.divide(value, 4, RoundingMode.HALF_UP);
        double ratioPercent = ratio.multiply(BigDecimal.valueOf(100)).doubleValue();

        if (ratio.compareTo(BigDecimal.valueOf(0.20)) <= 0) {
            // Excellent repair economy
            rationale.append(String.format("Repair investment is modest (%.1f%% of asset valuation). ", ratioPercent));
        } else if (ratio.compareTo(BigDecimal.valueOf(0.40)) <= 0) {
            score -= 15;
            rationale.append(String.format("Repair cost is moderate (%.1f%% of valuation). ", ratioPercent));
        } else if (ratio.compareTo(BigDecimal.valueOf(0.60)) <= 0) {
            score -= 30;
            rationale.append(String.format("Repair cost is elevated (%.1f%% of valuation). ", ratioPercent));
        } else if (ratio.compareTo(BigDecimal.valueOf(0.80)) <= 0) {
            score -= 50;
            rationale.append(String.format("Repair cost is high (%.1f%% of valuation), diminishing restoration feasibility. ", ratioPercent));
        } else {
            score -= 70;
            rationale.append(String.format("Restoration cost exceeds viable threshold (%.1f%% of valuation). ", ratioPercent));
        }

        // 2. Component Age & Obsolescence Factor
        DeviceCategoryType cat = (category != null) ? category : DeviceCategoryType.OTHER;
        int agePenalty = computeAgePenalty(cat, ageYears);
        score -= agePenalty;
        if (agePenalty > 0) {
            rationale.append(String.format("Device age (%d yrs) introduces component fatigue. ", ageYears));
        } else {
            rationale.append(String.format("Device vintage (%d yrs) is well within active lifecycle. ", ageYears));
        }

        // 3. Physical & Cosmetic Condition Factor
        DeviceConditionGrade cond = (condition != null) ? condition : DeviceConditionGrade.FAIR;
        switch (cond) {
            case GOOD -> {
                // No deduction
                rationale.append("Chassis and core enclosure are in solid preservation. ");
            }
            case FAIR -> {
                score -= 15;
                rationale.append("Moderate structural wear noted. ");
            }
            case POOR -> {
                score -= 30;
                rationale.append("Severe cosmetic and structural degradation present. ");
            }
        }

        // 4. Spare Parts Sourcing Feasibility
        PartAvailabilityStatus part = (partAvailability != null) ? partAvailability : PartAvailabilityStatus.UNKNOWN;
        switch (part) {
            case AVAILABLE -> {
                rationale.append("Replacement parts readily verified in workshop supplier network. ");
            }
            case UNKNOWN -> {
                score -= 15;
                rationale.append("Parts availability requires manual verification. ");
            }
            case UNAVAILABLE -> {
                score -= 35;
                rationale.append("Proprietary/obsolete components are unavailable, rendering repair improbable. ");
            }
        }

        // 5. Known Faults & Diagnostic Complexity
        if (knownIssues != null && !knownIssues.isEmpty()) {
            int criticalCount = 0;
            for (String issue : knownIssues) {
                String lower = issue.toLowerCase();
                if (lower.contains("board") || lower.contains("water") || lower.contains("motherboard") || lower.contains("short")) {
                    criticalCount++;
                    score -= 20;
                } else {
                    score -= 5;
                }
            }
            if (criticalCount > 0) {
                rationale.append(String.format("%d critical internal faults identified. ", criticalCount));
            }
        }

        // Clamp score between 5 and 95
        int finalScore = Math.max(5, Math.min(95, score));

        // Recommendation
        EngineRecommendationType recommendation = (finalScore >= 50)
                ? EngineRecommendationType.REPAIR
                : EngineRecommendationType.RECYCLE;

        // Confidence
        EngineConfidenceLevel confidence;
        if (finalScore >= 75 || finalScore <= 25) {
            confidence = EngineConfidenceLevel.HIGH;
        } else if (finalScore >= 40 && finalScore <= 60) {
            confidence = EngineConfidenceLevel.LOW;
        } else {
            confidence = EngineConfidenceLevel.MEDIUM;
        }

        double ewasteKg = calculateEstimatedEwasteSavedKg(cat);
        double co2eKg = calculateEstimatedCo2eSavedKg(cat);

        if (recommendation == EngineRecommendationType.REPAIR) {
            rationale.append(String.format("VERDICT: Technical restoration is economically and environmentally favorable (Est. ~%.1f kg e-waste diverted, ~%.0f kg CO₂e avoided vs new production).", ewasteKg, co2eKg));
        } else {
            rationale.append(String.format("VERDICT: Authorized zero-landfill e-waste recycling is the optimal ethical route (Est. ~%.1f kg high-value secondary materials recovered).", ewasteKg));
        }

        return new AssessmentResult(finalScore, recommendation, confidence, rationale.toString().trim());
    }

    public double calculateEstimatedEwasteSavedKg(DeviceCategoryType category) {
        if (category == null) return 0.5;
        return switch (category) {
            case SMARTPHONE -> 0.18;
            case TABLET -> 0.45;
            case LAPTOP -> 2.10;
            case DESKTOP -> 8.50;
            case OTHER -> 0.80;
        };
    }

    public double calculateEstimatedCo2eSavedKg(DeviceCategoryType category) {
        if (category == null) return 25.0;
        return switch (category) {
            case SMARTPHONE -> 45.0;
            case TABLET -> 60.0;
            case LAPTOP -> 180.0;
            case DESKTOP -> 240.0;
            case OTHER -> 30.0;
        };
    }

    private int computeAgePenalty(DeviceCategoryType category, int ageYears) {
        return switch (category) {
            case SMARTPHONE -> ageYears > 5 ? 25 : (ageYears > 3 ? 15 : 0);
            case LAPTOP -> ageYears > 7 ? 25 : (ageYears > 5 ? 15 : 0);
            case TABLET -> ageYears > 6 ? 25 : (ageYears > 4 ? 15 : 0);
            case DESKTOP -> ageYears > 8 ? 20 : (ageYears > 5 ? 10 : 0);
            case OTHER -> ageYears > 5 ? 20 : 0;
        };
    }
}

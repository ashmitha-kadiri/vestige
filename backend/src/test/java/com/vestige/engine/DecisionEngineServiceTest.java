package com.vestige.engine;

import com.vestige.model.enums.DeviceCategoryType;
import com.vestige.model.enums.DeviceConditionGrade;
import com.vestige.model.enums.EngineRecommendationType;
import com.vestige.model.enums.PartAvailabilityStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DecisionEngineServiceTest {

    private DecisionEngineService decisionEngineService;

    @BeforeEach
    void setUp() {
        decisionEngineService = new DecisionEngineService();
    }

    @Test
    @DisplayName("Should recommend REPAIR for low cost ratio, good condition, and available parts")
    void testRepairRecommendation() {
        AssessmentResult result = decisionEngineService.evaluate(
                DeviceCategoryType.SMARTPHONE,
                1,
                DeviceConditionGrade.GOOD,
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(45000),
                PartAvailabilityStatus.AVAILABLE,
                List.of("Cracked Glass Screen")
        );

        assertNotNull(result);
        assertEquals(EngineRecommendationType.REPAIR, result.recommendation());
        assertTrue(result.score() >= 50);
        assertNotNull(result.rationale());
    }

    @Test
    @DisplayName("Should recommend RECYCLE for high cost ratio, poor condition, old age, and unavailable parts")
    void testRecycleRecommendation() {
        AssessmentResult result = decisionEngineService.evaluate(
                DeviceCategoryType.LAPTOP,
                9,
                DeviceConditionGrade.POOR,
                BigDecimal.valueOf(18000),
                BigDecimal.valueOf(20000),
                PartAvailabilityStatus.UNAVAILABLE,
                List.of("Motherboard Short Circuit", "Broken Chassis", "Liquid Damage")
        );

        assertNotNull(result);
        assertEquals(EngineRecommendationType.RECYCLE, result.recommendation());
        assertTrue(result.score() < 50);
        assertNotNull(result.rationale());
    }

    @Test
    @DisplayName("Should calculate documented LCA sustainability metrics for various categories")
    void testSustainabilityCalculations() {
        assertEquals(0.18, decisionEngineService.calculateEstimatedEwasteSavedKg(DeviceCategoryType.SMARTPHONE));
        assertEquals(2.10, decisionEngineService.calculateEstimatedEwasteSavedKg(DeviceCategoryType.LAPTOP));
        assertEquals(8.50, decisionEngineService.calculateEstimatedEwasteSavedKg(DeviceCategoryType.DESKTOP));

        assertEquals(45.0, decisionEngineService.calculateEstimatedCo2eSavedKg(DeviceCategoryType.SMARTPHONE));
        assertEquals(180.0, decisionEngineService.calculateEstimatedCo2eSavedKg(DeviceCategoryType.LAPTOP));
        assertEquals(240.0, decisionEngineService.calculateEstimatedCo2eSavedKg(DeviceCategoryType.DESKTOP));
    }
}

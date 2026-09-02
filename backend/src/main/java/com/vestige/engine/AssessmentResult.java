package com.vestige.engine;

import com.vestige.model.enums.EngineConfidenceLevel;
import com.vestige.model.enums.EngineRecommendationType;

public record AssessmentResult(
    int score,
    EngineRecommendationType recommendation,
    EngineConfidenceLevel confidence,
    String rationale
) {}

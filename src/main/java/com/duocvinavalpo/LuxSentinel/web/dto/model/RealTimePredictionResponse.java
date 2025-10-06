package com.duocvinavalpo.LuxSentinel.web.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

// ==================== RESPONSE DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealTimePredictionResponse {
    private Integer predictedClass;
    private String className; // e.g., "CONFIRMED", "FALSE POSITIVE", "CANDIDATE"
    private Double confidence;
    private Map<String, Double> classProbabilities;
    private Long inferenceTimeMs;
    private String modelName;
    private String modelVersion;
}

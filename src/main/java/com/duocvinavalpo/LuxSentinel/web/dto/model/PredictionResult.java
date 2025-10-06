package com.duocvinavalpo.LuxSentinel.web.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

// ==================== RESPONSE DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResult {
    private Integer predictedClass;
    private Double probability;
    private Double confidenceScore;
    private Map<String, Double> allClassProbabilities;

    // MLP specific
    private List<Double> hiddenLayerActivations;

    // SVM specific
    private Double decisionFunction;
    private Integer supportVectors;

    private Long inferenceTimeMs;
    private String modelVersion;
    private String error;
}

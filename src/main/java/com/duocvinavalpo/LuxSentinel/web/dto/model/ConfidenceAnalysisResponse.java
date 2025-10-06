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
public class ConfidenceAnalysisResponse {
    private Long predictionId;
    private Double confidence;
    private String confidenceLevel; // "HIGH", "MEDIUM", "LOW"
    private Map<String, Double> featureImportance;
    private List<String> uncertaintyFactors;
    private String recommendation;
}

package com.duocvinavalpo.LuxSentinel.web.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

// ==================== RESPONSE DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelComparisonResponse {
    private Map<String, PredictionResult> modelResults; // key: modelName
    private double[] features;
    private String recommendedModel; // Based on confidence

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime comparedAt;
}

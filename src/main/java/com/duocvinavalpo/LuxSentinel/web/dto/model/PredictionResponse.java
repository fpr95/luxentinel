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
public class PredictionResponse {
    private Long predictionId;
    private Long deploymentId;
    private String modelName;
    private Integer predictedClass;
    private Double confidenceScore;
    private Map<String, Double> allClassProbabilities;
    private Long inferenceTimeMs;
    private String status; // "SUCCESS", "FAILED"
    private String errorMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime predictedAt;
}

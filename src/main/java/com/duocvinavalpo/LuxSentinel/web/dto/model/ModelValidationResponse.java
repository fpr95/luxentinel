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
public class ModelValidationResponse {
    private Long deploymentId;
    private Double accuracy;
    private Double precision;
    private Double recall;
    private Double f1Score;
    private Map<String, Object> confusionMatrix;
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validatedAt;
}

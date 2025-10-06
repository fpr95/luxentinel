package com.duocvinavalpo.LuxSentinel.web.dto.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

// ==================== REQUEST DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealTimePredictionRequest {
    @NotNull(message = "Deployment ID is required")
    private Long deploymentId;

    @NotNull(message = "Features are required")
    private double[] features;

    private Boolean includeExplanation = false;
}

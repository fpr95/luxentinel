package com.duocvinavalpo.LuxSentinel.web.dto.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.util.List;

// ==================== REQUEST DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelComparisonRequest {
    @NotNull(message = "Deployment IDs are required")
    @Size(min = 2, message = "At least 2 models are required for comparison")
    private List<Long> deploymentIds;

    @NotNull(message = "Features are required")
    private double[] features;
}


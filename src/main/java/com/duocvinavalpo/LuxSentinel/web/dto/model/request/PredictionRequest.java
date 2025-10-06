package com.duocvinavalpo.LuxSentinel.web.dto.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// ==================== REQUEST DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionRequest {
    @NotNull(message = "Deployment ID is required")
    private Long deploymentId;

    @NotNull(message = "Features are required")
    @Size(min = 1, message = "At least one feature is required")
    private double[] features;

    private Long datasetId; // Optional: link to dataset
    private String metadata; // Optional: additional context
}

package com.duocvinavalpo.LuxSentinel.web.dto.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

// ==================== REQUEST DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelValidationRequest {
    @NotNull(message = "Test dataset ID is required")
    private Long testDatasetId;

    private Integer maxSamples; // Optional: limit validation size
    private Boolean saveResults = true;
}

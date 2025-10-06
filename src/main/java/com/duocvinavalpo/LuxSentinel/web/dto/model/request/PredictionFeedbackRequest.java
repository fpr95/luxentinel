package com.duocvinavalpo.LuxSentinel.web.dto.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

// ==================== REQUEST DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionFeedbackRequest {
    @NotNull(message = "Actual class is required")
    private Integer actualClass;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating; // 1-5 stars

    private String comments;
    private Boolean isCorrect;
}

package com.duocvinavalpo.LuxSentinel.web.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// ==================== RESPONSE DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchPredictionResponse {
    private String batchId;
    private Integer totalSamples;
    private Integer successfulPredictions;
    private Integer failedPredictions;
    private List<PredictionResponse> predictions;
    private Long totalInferenceTimeMs;
    private String outputFormat;
    private String downloadUrl; // If results are saved to file

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;
}

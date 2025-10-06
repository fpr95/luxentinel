package com.duocvinavalpo.LuxSentinel.web.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// ==================== RESPONSE DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionHistoryResponse {
    private Long deploymentId;
    private String modelName;
    private Integer totalPredictions;
    private List<PredictionResponse> recentPredictions;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}

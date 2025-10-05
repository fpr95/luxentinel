package com.duocvinavalpo.LuxSentinel.web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PredictionDTO {
    private UUID id;
    private String result;
    private double probability;
    private LocalDateTime createdAt;
    private UUID userId;
    private UUID datasetId;
    private UUID modelId;
}

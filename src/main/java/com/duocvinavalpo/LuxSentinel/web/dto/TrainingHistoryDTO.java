package com.duocvinavalpo.LuxSentinel.web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
public class TrainingHistoryDTO {
    private UUID id;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String metrics;
    private Map<String, Object> hyperparameters;
    private UUID modelId;
    private UUID datasetId;
}

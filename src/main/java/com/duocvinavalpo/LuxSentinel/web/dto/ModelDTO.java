package com.duocvinavalpo.LuxSentinel.web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
public class ModelDTO {
    private UUID id;
    private String name;
    private String type;
    private String status;
    private LocalDateTime trainedAt;
    private Map<String, Object> hyperparameters;
    private UUID trainedById;
}

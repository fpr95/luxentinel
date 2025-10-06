package com.duocvinavalpo.LuxSentinel.web.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

// ==================== RESPONSE DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelMetadata {
    private Integer inputFeatures;
    private Integer outputClasses;
    private String architecture;
    private Map<String, Object> hyperparameters;
}

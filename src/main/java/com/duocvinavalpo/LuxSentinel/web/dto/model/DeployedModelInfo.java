package com.duocvinavalpo.LuxSentinel.web.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// ==================== RESPONSE DTO
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployedModelInfo {
    private Long id;
    private String modelName;
    private String modelType;
    private String version;
    private String description;
    private String filePath;
    private Long fileSize;
    private String checksum;
    private Boolean isActive;
    private Integer totalPredictions;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deployedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUsedAt;
}

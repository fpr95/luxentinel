package com.duocvinavalpo.LuxSentinel.entity.model;

import com.duocvinavalpo.LuxSentinel.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing a deployed ML model (MLP or SVM)
 */
@Entity
@Table(name = "deployed_models",
        uniqueConstraints = @UniqueConstraint(columnNames = {"model_name", "version"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeployedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

    @NotBlank
    @Column(name = "model_type", nullable = false, length = 20)
    private String modelType; // "MLP" or "SVM"

    @NotBlank
    @Column(name = "version", nullable = false, length = 50)
    private String version;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotBlank
    @Column(name = "model_file_path", nullable = false, length = 500)
    private String modelFilePath;

    @Column(name = "file_size")
    private Long fileSize; // in bytes

    @Column(name = "file_checksum", length = 64)
    private String fileChecksum; // MD5 or SHA256

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "input_features")
    private Integer inputFeatures; // Number of input features expected

    @Column(name = "output_classes")
    private Integer outputClasses; // Number of output classes

    @Column(name = "model_architecture", columnDefinition = "TEXT")
    private String modelArchitecture; // JSON or text description

    @Column(name = "total_predictions")
    private Integer totalPredictions = 0;

    @Column(name = "deployed_at")
    private LocalDateTime deployedAt;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deployed_by_user_id")
    private User deployedBy;

    // Helper methods
    public void incrementPredictionCount() {
        this.totalPredictions = (this.totalPredictions == null ? 0 : this.totalPredictions) + 1;
    }

    public boolean isModelType(String type) {
        return this.modelType != null && this.modelType.equalsIgnoreCase(type);
    }
}

package com.duocvinavalpo.LuxSentinel.entity.repository;

import com.duocvinavalpo.LuxSentinel.entity.model.DeployedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for DeployedModel entity
 */
@Repository
public interface DeployedModelRepository extends JpaRepository<DeployedModel, Long> {

    /**
     * Find all models by type (MLP or SVM)
     */
    List<DeployedModel> findByModelType(String modelType);

    /**
     * Find all active or inactive models
     */
    List<DeployedModel> findByIsActive(Boolean isActive);

    /**
     * Find models by type and active status
     */
    List<DeployedModel> findByModelTypeAndIsActive(String modelType, Boolean isActive);

    /**
     * Find model by name and version (unique constraint)
     */
    Optional<DeployedModel> findByModelNameAndVersion(String modelName, String version);

    /**
     * Check if model with name and version exists
     */
    boolean existsByModelNameAndVersion(String modelName, String version);

    /**
     * Find all versions of a model
     */
    List<DeployedModel> findByModelNameOrderByVersionDesc(String modelName);

    /**
     * Find the latest active model of a specific type
     */
    @Query("SELECT dm FROM DeployedModel dm WHERE dm.modelType = :modelType " +
            "AND dm.isActive = true ORDER BY dm.deployedAt DESC")
    Optional<DeployedModel> findLatestActiveModelByType(@Param("modelType") String modelType);

    /**
     * Find most used models (top N by prediction count)
     */
    @Query("SELECT dm FROM DeployedModel dm WHERE dm.isActive = true " +
            "ORDER BY dm.totalPredictions DESC")
    List<DeployedModel> findMostUsedModels();

    /**
     * Find models that haven't been used in a while
     */
    @Query("SELECT dm FROM DeployedModel dm WHERE dm.isActive = true " +
            "AND (dm.lastUsedAt IS NULL OR dm.lastUsedAt < :threshold)")
    List<DeployedModel> findUnusedModels(@Param("threshold") LocalDateTime threshold);

    /**
     * Find models deployed by a specific user
     */
    List<DeployedModel> findByDeployedBy_Id(Long userId);

    /**
     * Count active models by type
     */
    @Query("SELECT COUNT(dm) FROM DeployedModel dm WHERE dm.modelType = :modelType AND dm.isActive = true")
    long countActiveModelsByType(@Param("modelType") String modelType);

    /**
     * Find models with prediction count greater than threshold
     */
    @Query("SELECT dm FROM DeployedModel dm WHERE dm.totalPredictions > :threshold " +
            "ORDER BY dm.totalPredictions DESC")
    List<DeployedModel> findHighUsageModels(@Param("threshold") Integer threshold);

    /**
     * Get total storage size of all models
     */
    @Query("SELECT COALESCE(SUM(dm.fileSize), 0) FROM DeployedModel dm")
    Long getTotalStorageSize();

    /**
     * Find models by deployment date range
     */
    @Query("SELECT dm FROM DeployedModel dm WHERE dm.deployedAt BETWEEN :startDate AND :endDate " +
            "ORDER BY dm.deployedAt DESC")
    List<DeployedModel> findByDeploymentDateRange(@Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);
}

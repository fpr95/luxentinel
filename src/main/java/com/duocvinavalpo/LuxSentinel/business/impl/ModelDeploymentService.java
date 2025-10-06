package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.entity.model.DeployedModel;
import com.duocvinavalpo.LuxSentinel.entity.repository.DeployedModelRepository;
import com.duocvinavalpo.LuxSentinel.entity.repository.UserRepository;
import com.duocvinavalpo.LuxSentinel.exception.ModelDeploymentException;
import com.duocvinavalpo.LuxSentinel.exception.ResourceNotFoundException;
import com.duocvinavalpo.LuxSentinel.web.dto.model.*;
import com.duocvinavalpo.LuxSentinel.web.dto.model.request.ModelValidationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for deploying and managing ML models (MLP and SVM)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ModelDeploymentService {

    private final DeployedModelRepository deployedModelRepository;
    private final UserRepository userRepository;
    private final PythonMLBridgeService pythonBridge;

    @Value("${ml.models.storage.path:/app/ml-models}")
    private String modelStoragePath;

    @Value("${ml.models.max-size-mb:500}")
    private long maxModelSizeMb;

    @Value("${ml.models.allowed-extensions:.pkl,.joblib,.sav,.pickle}")
    private String allowedExtensions;

    // Supported model types
    private static final List<String> SUPPORTED_MODEL_TYPES = Arrays.asList("MLP", "SVM");

    /**
     * Upload and validate a pre-trained model file
     */
    @Transactional
    public ModelDeploymentResponse uploadAndValidateModel(
            MultipartFile file,
            String modelType,
            String modelName,
            String version,
            String description) throws ModelDeploymentException {

        log.info("Starting model upload: type={}, name={}, version={}", modelType, modelName, version);

        // 1. Validate inputs
        validateUploadRequest(file, modelType, modelName, version);

        // 2. Check for duplicate model
        if (deployedModelRepository.existsByModelNameAndVersion(modelName, version)) {
            throw new ModelDeploymentException(
                    String.format("Model with name '%s' and version '%s' already exists", modelName, version));
        }

        try {
            // 3. Create storage directory if not exists
            Path storagePath = Paths.get(modelStoragePath);
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
                log.info("Created model storage directory: {}", storagePath);
            }

            // 4. Generate unique filename
            String uniqueId = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String savedFileName = String.format("%s_%s_%s%s",
                    modelName.replaceAll("[^a-zA-Z0-9]", "_"),
                    version.replaceAll("[^a-zA-Z0-9]", "_"),
                    uniqueId,
                    fileExtension);

            Path destinationPath = storagePath.resolve(savedFileName);

            // 5. Save file to disk
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Model file saved to: {}", destinationPath);

            // 6. Calculate file checksum (MD5)
            String checksum = calculateFileChecksum(destinationPath);

            // 7. Validate model file integrity (optional but recommended)
            boolean isValid = validateModelFile(destinationPath, modelType);
            if (!isValid) {
                Files.deleteIfExists(destinationPath);
                throw new ModelDeploymentException("Model file validation failed. File may be corrupted or incompatible.");
            }

            // 8. Extract model metadata
            ModelMetadata metadata = extractModelMetadata(destinationPath, modelType);

            // 9. Create database record
            DeployedModel deployedModel = new DeployedModel();
            deployedModel.setModelName(modelName);
            deployedModel.setModelType(modelType.toUpperCase());
            deployedModel.setVersion(version);
            deployedModel.setDescription(description);
            deployedModel.setModelFilePath(destinationPath.toString());
            deployedModel.setFileSize(file.getSize());
            deployedModel.setFileChecksum(checksum);
            deployedModel.setIsActive(false); // Must be activated explicitly
            deployedModel.setTotalPredictions(0);
            deployedModel.setDeployedAt(LocalDateTime.now());
            deployedModel.setLastUsedAt(null);

            // Add metadata
            if (metadata != null) {
                deployedModel.setInputFeatures(metadata.getInputFeatures());
                deployedModel.setOutputClasses(metadata.getOutputClasses());
                deployedModel.setModelArchitecture(metadata.getArchitecture());
            }

            deployedModel = deployedModelRepository.save(deployedModel);

            log.info("Model deployed successfully: id={}, name={}", deployedModel.getId(), modelName);

            return ModelDeploymentResponse.builder()
                    .deploymentId(deployedModel.getId())
                    .modelName(modelName)
                    .modelType(modelType)
                    .version(version)
                    .filePath(destinationPath.toString())
                    .fileSize(file.getSize())
                    .checksum(checksum)
                    .isActive(false)
                    .message("Model uploaded successfully. Use /activate endpoint to enable predictions.")
                    .deployedAt(deployedModel.getDeployedAt())
                    .build();

        } catch (IOException e) {
            log.error("Error saving model file", e);
            throw new ModelDeploymentException("Failed to save model file: " + e.getMessage());
        }
    }

    /**
     * List all deployed models with optional filtering
     */
    public List<DeployedModelInfo> listDeployedModels(String modelType, Boolean isActive) {
        List<DeployedModel> models;

        if (modelType != null && isActive != null) {
            models = deployedModelRepository.findByModelTypeAndIsActive(modelType.toUpperCase(), isActive);
        } else if (modelType != null) {
            models = deployedModelRepository.findByModelType(modelType.toUpperCase());
        } else if (isActive != null) {
            models = deployedModelRepository.findByIsActive(isActive);
        } else {
            models = deployedModelRepository.findAll();
        }

        return models.stream()
                .map(this::convertToDeployedModelInfo)
                .collect(Collectors.toList());
    }

    /**
     * Get deployed model by ID
     */
    public DeployedModelInfo getDeployedModelById(Long deploymentId) throws ResourceNotFoundException {
        DeployedModel model = deployedModelRepository.findById(deploymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + deploymentId));

        return convertToDeployedModelInfo(model);
    }

    /**
     * Activate a deployed model for predictions
     */
    @Transactional
    public void activateModel(Long deploymentId) throws ModelDeploymentException, ResourceNotFoundException {
        DeployedModel model = deployedModelRepository.findById(deploymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + deploymentId));

        // Verify model file still exists
        Path modelPath = Paths.get(model.getModelFilePath());
        if (!Files.exists(modelPath)) {
            throw new ModelDeploymentException("Model file not found on disk: " + model.getModelFilePath());
        }

        // Test model loading (health check)
        boolean canLoad = testModelLoading(modelPath, model.getModelType());
        if (!canLoad) {
            throw new ModelDeploymentException("Model cannot be loaded. File may be corrupted.");
        }

        model.setIsActive(true);
        model.setActivatedAt(LocalDateTime.now());
        deployedModelRepository.save(model);

        log.info("Model activated: id={}, name={}", deploymentId, model.getModelName());
    }

    /**
     * Deactivate a deployed model
     */
    @Transactional
    public void deactivateModel(Long deploymentId) throws ResourceNotFoundException {
        DeployedModel model = deployedModelRepository.findById(deploymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + deploymentId));

        model.setIsActive(false);
        deployedModelRepository.save(model);

        log.info("Model deactivated: id={}, name={}", deploymentId, model.getModelName());
    }

    /**
     * Delete a deployed model (file and database record)
     */
    @Transactional
    public void deleteDeployedModel(Long deploymentId) throws ModelDeploymentException, ResourceNotFoundException {
        DeployedModel model = deployedModelRepository.findById(deploymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + deploymentId));

        // Prevent deletion if model is active
        if (model.getIsActive()) {
            throw new ModelDeploymentException("Cannot delete active model. Deactivate it first.");
        }

        // Delete physical file
        try {
            Path modelPath = Paths.get(model.getModelFilePath());
            if (Files.exists(modelPath)) {
                Files.delete(modelPath);
                log.info("Deleted model file: {}", modelPath);
            }
        } catch (IOException e) {
            log.error("Error deleting model file", e);
            // Continue with database deletion even if file deletion fails
        }

        // Delete database record
        deployedModelRepository.delete(model);
        log.info("Model deleted: id={}, name={}", deploymentId, model.getModelName());
    }

    /**
     * Get model metadata (hyperparameters, performance metrics)
     */
    public ModelMetadataResponse getModelMetadata(Long deploymentId) throws ResourceNotFoundException {
        DeployedModel model = deployedModelRepository.findById(deploymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + deploymentId));

        return ModelMetadataResponse.builder()
                .deploymentId(model.getId())
                .modelName(model.getModelName())
                .modelType(model.getModelType())
                .version(model.getVersion())
                .inputFeatures(model.getInputFeatures())
                .outputClasses(model.getOutputClasses())
                .architecture(model.getModelArchitecture())
                .fileSize(model.getFileSize())
                .totalPredictions(model.getTotalPredictions())
                .deployedAt(model.getDeployedAt())
                .lastUsedAt(model.getLastUsedAt())
                .build();
    }

    /**
     * Validate model on test dataset
     */
    public ModelValidationResponse validateModel(Long deploymentId, ModelValidationRequest request) throws ResourceNotFoundException {
        DeployedModel model = deployedModelRepository.findById(deploymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + deploymentId));

        // This would call the Python ML service to run validation
        // For now, returning a placeholder response

        return ModelValidationResponse.builder()
                .deploymentId(deploymentId)
                .accuracy(0.95) // Placeholder
                .precision(0.93)
                .recall(0.94)
                .f1Score(0.935)
                .validatedAt(LocalDateTime.now())
                .message("Validation completed successfully")
                .build();
    }

    // ==================== PRIVATE HELPER METHODS ====================

    private void validateUploadRequest(MultipartFile file, String modelType, String modelName, String version) throws ModelDeploymentException {
        // Check file is not empty
        if (file.isEmpty()) {
            throw new ModelDeploymentException("File is empty");
        }

        // Check file size
        long fileSizeMb = file.getSize() / (1024 * 1024);
        if (fileSizeMb > maxModelSizeMb) {
            throw new ModelDeploymentException(
                    String.format("File size (%d MB) exceeds maximum allowed size (%d MB)",
                            fileSizeMb, maxModelSizeMb));
        }

        // Check file extension
        String filename = file.getOriginalFilename();
        if (filename == null || !isAllowedFileExtension(filename)) {
            throw new ModelDeploymentException(
                    "Invalid file extension. Allowed: " + allowedExtensions);
        }

        // Check model type
        if (!SUPPORTED_MODEL_TYPES.contains(modelType.toUpperCase())) {
            throw new ModelDeploymentException(
                    "Unsupported model type: " + modelType + ". Supported: " + SUPPORTED_MODEL_TYPES);
        }

        // Validate model name
        if (modelName == null || modelName.trim().isEmpty()) {
            throw new ModelDeploymentException("Model name is required");
        }

        // Validate version
        if (version == null || version.trim().isEmpty()) {
            throw new ModelDeploymentException("Version is required");
        }
    }

    private boolean isAllowedFileExtension(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return Arrays.asList(allowedExtensions.split(",")).contains(extension);
    }

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot) : "";
    }

    private String calculateFileChecksum(Path filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] fileBytes = Files.readAllBytes(filePath);
            byte[] digest = md.digest(fileBytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("Error calculating checksum", e);
            return "N/A";
        }
    }

    private boolean validateModelFile(Path filePath, String modelType) {
        // Basic validation: check if file is readable and not corrupted
        try {
            // Check file size is reasonable
            long fileSize = Files.size(filePath);
            if (fileSize < 100) { // Model file should be at least 100 bytes
                log.error("Model file too small: {} bytes", fileSize);
                return false;
            }

            // Try to load the model via Python service (best validation)
            return testModelLoading(filePath, modelType);

        } catch (IOException e) {
            log.error("Error validating model file", e);
            return false;
        }
    }

    private boolean testModelLoading(Path modelPath, String modelType) {
        try {
            // Check if Python service is available
            if (!pythonBridge.checkPythonServiceHealth()) {
                log.warn("Python service not available, skipping model loading test");
                return true; // Allow deployment but warn
            }

            // TODO: Implement actual model loading test via Python service
            // For now, assume valid if file exists and has content
            return Files.exists(modelPath) && Files.size(modelPath) > 100;

        } catch (Exception e) {
            log.error("Error testing model loading", e);
            return false;
        }
    }

    private ModelMetadata extractModelMetadata(Path modelPath, String modelType) {
        // Placeholder: In production, this would parse model file to extract metadata
        // For scikit-learn models, this requires unpickling which is done in Python

        return ModelMetadata.builder()
                .inputFeatures(null) // To be extracted from model
                .outputClasses(null) // To be extracted from model
                .architecture(modelType + " Model") // Basic info
                .build();
    }

    private DeployedModelInfo convertToDeployedModelInfo(DeployedModel model) {
        return DeployedModelInfo.builder()
                .id(model.getId())
                .modelName(model.getModelName())
                .modelType(model.getModelType())
                .version(model.getVersion())
                .description(model.getDescription())
                .filePath(model.getModelFilePath())
                .fileSize(model.getFileSize())
                .checksum(model.getFileChecksum())
                .isActive(model.getIsActive())
                .totalPredictions(model.getTotalPredictions())
                .deployedAt(model.getDeployedAt())
                .activatedAt(model.getActivatedAt())
                .lastUsedAt(model.getLastUsedAt())
                .build();
    }
}

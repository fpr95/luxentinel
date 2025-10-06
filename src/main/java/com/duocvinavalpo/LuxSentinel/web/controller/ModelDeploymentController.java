package com.duocvinavalpo.LuxSentinel.web.controller;

import com.duocvinavalpo.LuxSentinel.business.impl.ModelDeploymentService;
import com.duocvinavalpo.LuxSentinel.exception.ModelDeploymentException;
import com.duocvinavalpo.LuxSentinel.exception.ResourceNotFoundException;
import com.duocvinavalpo.LuxSentinel.web.dto.model.DeployedModelInfo;
import com.duocvinavalpo.LuxSentinel.web.dto.model.ModelDeploymentResponse;
import com.duocvinavalpo.LuxSentinel.web.dto.model.ModelMetadataResponse;
import com.duocvinavalpo.LuxSentinel.web.dto.model.ModelValidationResponse;
import com.duocvinavalpo.LuxSentinel.web.dto.model.request.ModelValidationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/model-deployment")
@RequiredArgsConstructor
@Tag(name = "Model Deployment", description = "Deploy and manage pre-trained ML models")
public class ModelDeploymentController {

    private final ModelDeploymentService deploymentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DATA_SCIENTIST')")
    @Operation(summary = "Upload pre-trained model (MLP/SVM)")
    public ResponseEntity<ModelDeploymentResponse> uploadModel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("modelType") String modelType, // "MLP" or "SVM"
            @RequestParam("modelName") String modelName,
            @RequestParam("version") String version,
            @RequestParam(value = "description", required = false) String description) throws ModelDeploymentException {

        ModelDeploymentResponse response = deploymentService.uploadAndValidateModel(
                file, modelType, modelName, version, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/deployed")
    @Operation(summary = "List all deployed models")
    public ResponseEntity<List<DeployedModelInfo>> listDeployedModels(
            @RequestParam(required = false) String modelType,
            @RequestParam(required = false) Boolean isActive) {

        List<DeployedModelInfo> models = deploymentService.listDeployedModels(modelType, isActive);
        return ResponseEntity.ok(models);
    }

    @GetMapping("/{deploymentId}")
    @Operation(summary = "Get deployed model details")
    public ResponseEntity<DeployedModelInfo> getDeployedModel(@PathVariable Long deploymentId) throws ResourceNotFoundException {
        DeployedModelInfo model = deploymentService.getDeployedModelById(deploymentId);
        return ResponseEntity.ok(model);
    }

    @PostMapping("/{deploymentId}/activate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DATA_SCIENTIST')")
    @Operation(summary = "Activate a deployed model for predictions")
    public ResponseEntity<Void> activateModel(@PathVariable Long deploymentId) throws ModelDeploymentException, ResourceNotFoundException {
        deploymentService.activateModel(deploymentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deploymentId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate a deployed model")
    public ResponseEntity<Void> deactivateModel(@PathVariable Long deploymentId) throws ResourceNotFoundException {
        deploymentService.deactivateModel(deploymentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{deploymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a deployed model")
    public ResponseEntity<Void> deleteDeployedModel(@PathVariable Long deploymentId) throws ModelDeploymentException, ResourceNotFoundException {
        deploymentService.deleteDeployedModel(deploymentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{deploymentId}/metadata")
    @Operation(summary = "Get model metadata (hyperparameters, performance metrics)")
    public ResponseEntity<ModelMetadataResponse> getModelMetadata(@PathVariable Long deploymentId) throws ResourceNotFoundException {
        ModelMetadataResponse metadata = deploymentService.getModelMetadata(deploymentId);
        return ResponseEntity.ok(metadata);
    }

    @PostMapping("/{deploymentId}/validate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DATA_SCIENTIST')")
    @Operation(summary = "Validate model on test dataset")
    public ResponseEntity<ModelValidationResponse> validateModel(
            @PathVariable Long deploymentId,
            @Valid @RequestBody ModelValidationRequest request) throws ResourceNotFoundException {

        ModelValidationResponse response = deploymentService.validateModel(deploymentId, request);
        return ResponseEntity.ok(response);
    }
}

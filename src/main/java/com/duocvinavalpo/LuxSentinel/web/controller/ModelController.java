package com.duocvinavalpo.LuxSentinel.web.controller;

import com.duocvinavalpo.LuxSentinel.business.ModelService;
import com.duocvinavalpo.LuxSentinel.model.Model;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/models")
public class ModelController {

    private final ModelService modelService;

    @PostMapping
    public ResponseEntity<Model> createModel(@RequestBody Model model) {
        return ResponseEntity.ok(modelService.createModel(model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable UUID id) {
        return modelService.getModelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Model>> getAllModels() {
        return ResponseEntity.ok(modelService.getAllModels());
    }

    @PutMapping("/{id}/hyperparameters")
    public ResponseEntity<Model> updateHyperparameters(
            @PathVariable UUID id,
            @RequestBody String hyperparameters) {
        return ResponseEntity.ok(modelService.updateHyperparameters(id, hyperparameters));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable UUID id) {
        modelService.deleteModel(id);
        return ResponseEntity.noContent().build();
    }
}

package com.duocvinavalpo.LuxSentinel.web.controller;

import com.duocvinavalpo.LuxSentinel.business.PredictionService;
import com.duocvinavalpo.LuxSentinel.entity.Prediction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/run")
    public ResponseEntity<Prediction> runPrediction(@RequestBody Prediction prediction) {
        // Aquí podrías agregar lógica extra para validar dataset/model antes
        return ResponseEntity.ok(predictionService.runPrediction(prediction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prediction> getPredictionById(@PathVariable UUID id) {
        return predictionService.getPredictionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Prediction>> getAllPredictions() {
        return ResponseEntity.ok(predictionService.getAllPredictions());
    }
}

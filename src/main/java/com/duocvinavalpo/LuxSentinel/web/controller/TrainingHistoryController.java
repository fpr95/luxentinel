package com.duocvinavalpo.LuxSentinel.web.controller;

import com.duocvinavalpo.LuxSentinel.business.TrainingHistoryService;
import com.duocvinavalpo.LuxSentinel.model.TrainingHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/training-history")
@RequiredArgsConstructor
public class TrainingHistoryController {

    private final TrainingHistoryService trainingHistoryService;

    @PostMapping
    public ResponseEntity<TrainingHistory> logTraining(@RequestBody TrainingHistory history) {
        return ResponseEntity.ok(trainingHistoryService.logTraining(history));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingHistory> getTrainingHistoryById(@PathVariable UUID id) {
        return trainingHistoryService.getTrainingHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TrainingHistory>> getAllTrainingHistories() {
        return ResponseEntity.ok(trainingHistoryService.getAllTrainingHistories());
    }
}

package com.duocvinavalpo.LuxSentinel.business;

import com.duocvinavalpo.LuxSentinel.entity.Dataset;
import com.duocvinavalpo.LuxSentinel.entity.Model;
import com.duocvinavalpo.LuxSentinel.entity.TrainingHistory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingHistoryService {
    TrainingHistory logTraining(TrainingHistory history);
    Optional<TrainingHistory> getTrainingHistoryById(UUID id);
    List<TrainingHistory> getTrainingHistoryByModel(Model model);
    List<TrainingHistory> getTrainingHistoryByDataset(Dataset dataset);
    List<TrainingHistory> getAllTrainingHistories();
}

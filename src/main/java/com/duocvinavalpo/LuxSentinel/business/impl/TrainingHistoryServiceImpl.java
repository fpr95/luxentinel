package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.business.TrainingHistoryService;
import com.duocvinavalpo.LuxSentinel.model.Dataset;
import com.duocvinavalpo.LuxSentinel.model.Model;
import com.duocvinavalpo.LuxSentinel.model.TrainingHistory;
import com.duocvinavalpo.LuxSentinel.model.repository.TrainingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingHistoryServiceImpl implements TrainingHistoryService {

    private final TrainingHistoryRepository trainingHistoryRepository;

    @Override
    public TrainingHistory logTraining(TrainingHistory history) {
        return trainingHistoryRepository.save(history);
    }

    @Override
    public Optional<TrainingHistory> getTrainingHistoryById(UUID id) {
        return trainingHistoryRepository.findById(id);
    }

    @Override
    public List<TrainingHistory> getTrainingHistoryByModel(Model model) {
        return trainingHistoryRepository.findByModel(model);
    }

    @Override
    public List<TrainingHistory> getTrainingHistoryByDataset(Dataset dataset) {
        return trainingHistoryRepository.findByDataset(dataset);
    }

    @Override
    public List<TrainingHistory> getAllTrainingHistories() {
        return trainingHistoryRepository.findAll();
    }
}

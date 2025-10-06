package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.business.PredictionService;
import com.duocvinavalpo.LuxSentinel.entity.Dataset;
import com.duocvinavalpo.LuxSentinel.entity.Model;
import com.duocvinavalpo.LuxSentinel.entity.Prediction;
import com.duocvinavalpo.LuxSentinel.entity.User;
import com.duocvinavalpo.LuxSentinel.entity.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final PredictionRepository predictionRepository;

    @Override
    public Prediction runPrediction(Prediction prediction) {
        // Aquí normalmente llamarías al microservicio Python para ejecutar el modelo
        return predictionRepository.save(prediction);
    }

    @Override
    public Optional<Prediction> getPredictionById(UUID id) {
        return predictionRepository.findById(id);
    }

    @Override
    public List<Prediction> getPredictionsByUser(User user) {
        return predictionRepository.findByUser(user);
    }

    @Override
    public List<Prediction> getPredictionsByDataset(Dataset dataset) {
        return predictionRepository.findByDataset(dataset);
    }

    @Override
    public List<Prediction> getPredictionsByModel(Model model) {
        return predictionRepository.findByModel(model);
    }

    @Override
    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll();
    }
}

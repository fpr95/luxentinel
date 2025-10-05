package com.duocvinavalpo.LuxSentinel.business;

import com.duocvinavalpo.LuxSentinel.model.Dataset;
import com.duocvinavalpo.LuxSentinel.model.Model;
import com.duocvinavalpo.LuxSentinel.model.Prediction;
import com.duocvinavalpo.LuxSentinel.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PredictionService {
    Prediction runPrediction(Prediction prediction);
    Optional<Prediction> getPredictionById(UUID id);
    List<Prediction> getPredictionsByUser(User user);
    List<Prediction> getPredictionsByDataset(Dataset dataset);
    List<Prediction> getPredictionsByModel(Model model);
    List<Prediction> getAllPredictions();
}

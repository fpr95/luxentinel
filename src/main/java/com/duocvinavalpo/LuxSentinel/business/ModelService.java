package com.duocvinavalpo.LuxSentinel.business;

import com.duocvinavalpo.LuxSentinel.entity.Model;
import com.duocvinavalpo.LuxSentinel.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelService {
    Model createModel(Model model);
    Optional<Model> getModelById(UUID id);
    List<Model> getModelsByUser(User user);
    List<Model> getModelsByStatus(String status);
    List<Model> getModelsByName(String name);
    Model updateHyperparameters(UUID id, String hyperparameters);
    List<Model> getAllModels();
    void deleteModel(UUID id);
}

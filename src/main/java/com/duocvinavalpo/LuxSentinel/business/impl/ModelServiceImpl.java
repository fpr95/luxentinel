package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.model.Model;
import com.duocvinavalpo.LuxSentinel.model.User;
import com.duocvinavalpo.LuxSentinel.model.repository.ModelRepository;
import com.duocvinavalpo.LuxSentinel.model.repository.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;

    @Override
    public Model createModel(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public Optional<Model> getModelById(UUID id) {
        return modelRepository.findById(id);
    }

    @Override
    public List<Model> getModelsByUser(User user) {
        return modelRepository.findByTrainedBy(user);
    }

    @Override
    public List<Model> getModelsByStatus(String status) {
        return modelRepository.findByStatus(status);
    }

    @Override
    public List<Model> getModelsByName(String name) {
        return modelRepository.findByName(name);
    }

    @Override
    public Model updateHyperparameters(UUID id, String hyperparameters) {
        return modelRepository.findById(id).map(model -> {
            model.setHyperparameters(hyperparameters);
            model.setLastTrainedAt(LocalDateTime.now());
            return modelRepository.save(model);
        }).orElseThrow(() -> new RuntimeException("Model not found"));
    }

    @Override
    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    @Override
    public void deleteModel(UUID id) {
        modelRepository.deleteById(id);
    }
}

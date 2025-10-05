package com.duocvinavalpo.LuxSentinel.model.repository;

import com.duocvinavalpo.LuxSentinel.model.Dataset;
import com.duocvinavalpo.LuxSentinel.model.Model;
import com.duocvinavalpo.LuxSentinel.model.Prediction;
import com.duocvinavalpo.LuxSentinel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PredictionRepository extends JpaRepository<Prediction, UUID> {

    List<Prediction> findByUser(User user);

    List<Prediction> findByDataset(Dataset dataset);

    List<Prediction> findByModel(Model model);
}

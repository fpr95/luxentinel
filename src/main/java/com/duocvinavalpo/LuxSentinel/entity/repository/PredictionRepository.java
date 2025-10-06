package com.duocvinavalpo.LuxSentinel.entity.repository;

import com.duocvinavalpo.LuxSentinel.entity.Dataset;
import com.duocvinavalpo.LuxSentinel.entity.Model;
import com.duocvinavalpo.LuxSentinel.entity.Prediction;
import com.duocvinavalpo.LuxSentinel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PredictionRepository extends JpaRepository<Prediction, UUID> {

    List<Prediction> findByUser(User user);

    List<Prediction> findByDataset(Dataset dataset);

    List<Prediction> findByModel(Model model);
}

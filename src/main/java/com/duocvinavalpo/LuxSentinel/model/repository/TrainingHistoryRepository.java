package com.duocvinavalpo.LuxSentinel.model.repository;

import com.duocvinavalpo.LuxSentinel.model.Dataset;
import com.duocvinavalpo.LuxSentinel.model.Model;
import com.duocvinavalpo.LuxSentinel.model.TrainingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingHistoryRepository extends JpaRepository<TrainingHistory, UUID> {

    List<TrainingHistory> findByModel(Model model);

    List<TrainingHistory> findByDataset(Dataset dataset);
}

package com.duocvinavalpo.LuxSentinel.entity.repository;

import com.duocvinavalpo.LuxSentinel.entity.Dataset;
import com.duocvinavalpo.LuxSentinel.entity.Model;
import com.duocvinavalpo.LuxSentinel.entity.TrainingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingHistoryRepository extends JpaRepository<TrainingHistory, UUID> {

    List<TrainingHistory> findByModel(Model model);

    List<TrainingHistory> findByDataset(Dataset dataset);
}

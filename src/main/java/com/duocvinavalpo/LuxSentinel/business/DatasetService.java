package com.duocvinavalpo.LuxSentinel.business;

import com.duocvinavalpo.LuxSentinel.entity.Dataset;
import com.duocvinavalpo.LuxSentinel.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DatasetService {
    Dataset uploadDataset(Dataset dataset);
    Optional<Dataset> getDatasetById(UUID id);
    List<Dataset> getDatasetsByUser(User user);
    List<Dataset> getDatasetsByType(String type);
    List<Dataset> getAllDatasets();
    void deleteDataset(UUID id);
}

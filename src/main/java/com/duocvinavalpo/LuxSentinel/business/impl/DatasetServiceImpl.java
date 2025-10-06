package com.duocvinavalpo.LuxSentinel.business.impl;

import com.duocvinavalpo.LuxSentinel.business.DatasetService;
import com.duocvinavalpo.LuxSentinel.entity.Dataset;
import com.duocvinavalpo.LuxSentinel.entity.User;
import com.duocvinavalpo.LuxSentinel.entity.repository.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DatasetServiceImpl implements DatasetService {

    private final DatasetRepository datasetRepository;

    @Override
    public Dataset uploadDataset(Dataset dataset) {
        return datasetRepository.save(dataset);
    }

    @Override
    public Optional<Dataset> getDatasetById(UUID id) {
        return datasetRepository.findById(id);
    }

    @Override
    public List<Dataset> getDatasetsByUser(User user) {
        return datasetRepository.findByUploadedBy(user);
    }

    @Override
    public List<Dataset> getDatasetsByType(String type) {
        return datasetRepository.findByType(type);
    }

    @Override
    public List<Dataset> getAllDatasets() {
        return datasetRepository.findAll();
    }

    @Override
    public void deleteDataset(UUID id) {
        datasetRepository.deleteById(id);
    }
}

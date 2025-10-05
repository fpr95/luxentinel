package com.duocvinavalpo.LuxSentinel.model.repository;

import com.duocvinavalpo.LuxSentinel.model.Dataset;
import com.duocvinavalpo.LuxSentinel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DatasetRepository extends JpaRepository<Dataset, UUID> {

    List<Dataset> findByUploadedBy(User user);

    List<Dataset> findByType(String type);
}

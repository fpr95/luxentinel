package com.duocvinavalpo.LuxSentinel.entity.repository;

import com.duocvinavalpo.LuxSentinel.entity.Dataset;
import com.duocvinavalpo.LuxSentinel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DatasetRepository extends JpaRepository<Dataset, UUID> {

    List<Dataset> findByUploadedBy(User user);

    List<Dataset> findByType(String type);
}

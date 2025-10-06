package com.duocvinavalpo.LuxSentinel.entity.repository;

import com.duocvinavalpo.LuxSentinel.entity.Model;
import com.duocvinavalpo.LuxSentinel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID> {

    List<Model> findByTrainedBy(User user);

    List<Model> findByStatus(String status);

    List<Model> findByName(String name); // SVM, MLP, CNN
}

package com.duocvinavalpo.LuxSentinel.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "models")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String name; // SVM, MLP, CNN

    @Column(length = 255)
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String hyperparameters; // JSON

    @Column(nullable = false, length = 20)
    private String status; // TRAINED, IN_TRAINING, DEPLOYED

    private LocalDateTime lastTrainedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trained_by")
    private User trainedBy;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prediction> predictions = new ArrayList<>();

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingHistory> trainingHistories = new ArrayList<>();
}

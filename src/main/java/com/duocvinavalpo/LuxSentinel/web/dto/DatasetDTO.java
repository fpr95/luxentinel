package com.duocvinavalpo.LuxSentinel.web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DatasetDTO {
    private UUID id;
    private String name;
    private String type;
    private String location;
    private LocalDateTime uploadDate;
    private UUID uploadedById;
}

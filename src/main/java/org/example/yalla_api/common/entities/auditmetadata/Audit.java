package org.example.yalla_api.common.entities.auditmetadata;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entity;
    private Long entityId;
    private String action;
    @Column(name = "modified_by")
    private String user;
    private LocalDateTime timestamp;

    // Getters and setters
}

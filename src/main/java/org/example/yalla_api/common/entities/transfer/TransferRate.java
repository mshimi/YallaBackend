package org.example.yalla_api.common.entities.transfer;

import jakarta.persistence.*;
import org.example.yalla_api.common.entities.core.Area;

import java.time.LocalDateTime;

@Entity
public class TransferRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @ManyToOne
    @JoinColumn(name = "source_area_id", nullable = false)
    private Area sourceArea;


    @ManyToOne
    @JoinColumn(name = "destination_area_id", nullable = false)
    private Area destinationArea;


    private Double ratePerPerson;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

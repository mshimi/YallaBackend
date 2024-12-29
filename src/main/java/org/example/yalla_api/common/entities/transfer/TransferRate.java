package org.example.yalla_api.common.entities.transfer;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.core.Area;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;


}

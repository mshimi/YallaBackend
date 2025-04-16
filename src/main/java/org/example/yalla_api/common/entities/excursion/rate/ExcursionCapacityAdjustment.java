package org.example.yalla_api.common.entities.excursion.rate;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ExcursionCapacityAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int delta; // +2 or -1 etc.

    private String reason; // Optional: e.g., "group cancel", "manual override"

    private LocalDateTime timestamp = LocalDateTime.now();

    private String modifiedBy; // Optional: admin username or ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_id")
    private OneDayExcursionRate rate;
}
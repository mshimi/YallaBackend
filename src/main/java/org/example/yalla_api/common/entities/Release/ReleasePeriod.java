package org.example.yalla_api.common.entities.Release;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Data
public class ReleasePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private LocalDate startDate;

    @Column(nullable = true)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer releaseDays; // Minimum days before booking is allowed

    @Column(nullable = false)
    private Boolean isActive = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private Boolean isGeneral = false; // General release or specific release

    // Determines if the current period applies to the given booking date
    public boolean isApplicable(LocalDate bookingDate) {
        return isActive && !isGeneral &&
                (bookingDate.isAfter(startDate.minusDays(1)) && bookingDate.isBefore(endDate.plusDays(1)));
    }
}

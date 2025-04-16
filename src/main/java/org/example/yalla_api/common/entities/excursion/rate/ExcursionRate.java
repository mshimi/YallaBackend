package org.example.yalla_api.common.entities.excursion.rate;


import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.excursion.shuttleBus.ShuttleBusTransfer;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "excursion_rate_type")
@Data
public abstract class ExcursionRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private BigDecimal rate;

    private Integer releaseDays;

    private Integer capacity;

    private Integer remaining;

    private boolean active = true;

    private boolean stopSale = false;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private ExcursionOffer offer;

    @OneToMany(mappedBy = "rate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ExcursionCapacityAdjustment> capacityAdjustments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private DayOfWeek weekday;

    @PrePersist
    @PreUpdate
    public void setWeekdayFromDate() {
        if (this.date != null) {
            this.weekday = this.date.getDayOfWeek();
        }
    }
}
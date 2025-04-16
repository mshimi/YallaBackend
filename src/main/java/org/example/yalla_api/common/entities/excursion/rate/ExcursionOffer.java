package org.example.yalla_api.common.entities.excursion.rate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;



import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "offer_type")
@Data
public abstract class ExcursionOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active = true;

    private boolean appliedForChildren = true;

    private Integer releaseDays; // e.g., must be booked X days before travel date

    private Integer capacity;     // how many total times this offer can be used
    private Integer remaining;    // updated when someone books using it

    @Column(name = "offer_type", insertable = false, updatable = false)
    private String type;

    /**
     * Calculates the final price based on the given base rate.
     */
    public abstract BigDecimal calculate(BigDecimal baseRate);

    public boolean isAvailable() {
        return active && (remaining == null || remaining > 0);
    }

    /**
     * Decrements remaining capacity, if applicable.
     */
    public void use() {
        if (remaining != null && remaining > 0) {
            remaining--;
        }
    }
}
package org.example.yalla_api.common.entities.excursion.excursionchildrenpolicy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ExcursionAgeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ageFrom;
    private Integer ageTo;

    private Double basePrice = 1.0;
    private Double paxValue = 1.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "children_policy_id")
    private ExcursionChildrenPolicy childrenPolicy;

    @PrePersist
    @PreUpdate
    public void validateAgeRange() {
        if (ageFrom == null || ageTo == null || ageFrom < 0 || ageTo < 0 || ageFrom > ageTo) {
            throw new IllegalArgumentException("Invalid age range configuration.");
        }
    }
}

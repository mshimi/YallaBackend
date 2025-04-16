package org.example.yalla_api.common.entities.excursion.excursionchildrenpolicy;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ExcursionChildrenPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive = true;

    @OneToMany(mappedBy = "childrenPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExcursionAgeRange> ageRanges;

    public EvaluatedChildAgeResult evaluateAge(int age) {
        return ageRanges.stream()
                .filter(range -> age >= range.getAgeFrom() && age <= range.getAgeTo())
                .findFirst()
                .map(range -> new EvaluatedChildAgeResult(range.getPaxValue(), range.getBasePrice()))
                .orElseGet(() -> new EvaluatedChildAgeResult(1.0, 1.0));
    }

    /**
     * Helper class to represent evaluation result.
     */
    public record EvaluatedChildAgeResult(double paxValue, double priceValue) {}
}

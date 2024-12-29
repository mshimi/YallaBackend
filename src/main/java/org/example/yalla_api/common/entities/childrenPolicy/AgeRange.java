package org.example.yalla_api.common.entities.childrenPolicy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AgeRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ageFrom;
    private Integer ageTo ;
    private Double basePrice = 1.0;
    private Double paxValue = 1.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "children_policy_id")
    private ChildrenPolicy childrenPolicy;

    @PrePersist
    @PreUpdate
    public void validateAgeRange() {

        if(ageFrom == null || ageTo == null ){
            throw new IllegalArgumentException("Invalid age range");
        } else

        if(ageFrom < 0 || ageTo < 0){
            throw new IllegalArgumentException("Invalid age range: ages can't be negative");
        } else


        if (  ageFrom > ageTo) {
            throw new IllegalArgumentException("Invalid age range: ageFrom must be less than ageTo.");
        }
    }
}

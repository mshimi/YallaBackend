package org.example.yalla_api.common.entities.excursion.shuttleBus;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.excursion.excursionchildrenpolicy.ExcursionAgeRange;

import java.util.List;

@Entity
@Data
public class ShuttleBusChildrenPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active = true;

    @OneToMany(mappedBy = "childrenPolicy", cascade = CascadeType.ALL)
    private List<ShuttleBusAgeRange> ageRanges;
}

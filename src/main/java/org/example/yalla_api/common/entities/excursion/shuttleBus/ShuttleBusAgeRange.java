package org.example.yalla_api.common.entities.excursion.shuttleBus;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class ShuttleBusAgeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // fields...

    @ManyToOne
    @JoinColumn(name = "children_policy_id")
    private ShuttleBusChildrenPolicy childrenPolicy;
}

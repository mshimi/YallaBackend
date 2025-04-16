package org.example.yalla_api.common.entities.excursion.excursions;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.yalla_api.common.entities.excursion.rate.OneDayExcursionRate;
import org.example.yalla_api.common.entities.excursion.rate.PrivateExcursionRate;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("PRIVATE")
public class PrivateExcursion extends Excursion {

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PrivateExcursionRate> rates;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferRequirement transferRequirement = TransferRequirement.NOT_INCLUDED;


    private Integer minPax;

    private Integer maxPax;

}

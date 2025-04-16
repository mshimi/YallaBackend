package org.example.yalla_api.common.entities.excursion.rate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.yalla_api.common.entities.excursion.excursions.PrivateExcursion;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("PRIVATE")
@Data
public class PrivateExcursionRate extends ExcursionRate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_id")
    private PrivateExcursion excursion;

    // Optional: pricing tiers per number of pax
    // e.g. Map<Integer, BigDecimal> paxToPrice or List<PaxRate>
}

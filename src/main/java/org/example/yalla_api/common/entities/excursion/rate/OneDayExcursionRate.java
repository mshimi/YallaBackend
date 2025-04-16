package org.example.yalla_api.common.entities.excursion.rate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.yalla_api.common.entities.excursion.excursions.OneDayExcursion;
import org.example.yalla_api.common.entities.excursion.shuttleBus.ShuttleBusTransfer;


@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ONE_DAY")
@Data
public class OneDayExcursionRate extends ExcursionRate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_id")
    private OneDayExcursion excursion;
}
package org.example.yalla_api.common.entities.excursion.excursions;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.yalla_api.common.entities.excursion.rate.OneDayExcursionRate;
import org.example.yalla_api.common.entities.excursion.shuttleBus.ShuttleBusTransfer;


import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("ONE_DAY")
public class OneDayExcursion extends Excursion {

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OneDayExcursionRate> rates;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferRequirement transferRequirement = TransferRequirement.NOT_INCLUDED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shuttle_transfer_id")
    private ShuttleBusTransfer shuttleBusTransfer;

}

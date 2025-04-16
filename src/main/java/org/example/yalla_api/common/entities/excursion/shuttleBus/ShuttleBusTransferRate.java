package org.example.yalla_api.common.entities.excursion.shuttleBus;


import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.core.Area;

import java.math.BigDecimal;

@Entity
@Data
public class ShuttleBusTransferRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shuttle_transfer_id")
    private ShuttleBusTransfer shuttleTransfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_area_id")
    private Area startArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_area_id")
    private Area endArea;

    private BigDecimal price;
}

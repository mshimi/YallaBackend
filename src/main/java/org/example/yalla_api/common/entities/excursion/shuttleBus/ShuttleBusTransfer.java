package org.example.yalla_api.common.entities.excursion.shuttleBus;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Data
public class ShuttleBusTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active = true;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ShuttleBusChildrenPolicy childrenPolicy;

    @OneToMany(mappedBy = "shuttleTransfer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ShuttleBusTransferRate> rates;
}

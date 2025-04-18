package org.example.yalla_api.common.entities.excursion;

/**
import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.excursion.excursions.Excursion;
import org.example.yalla_api.common.entities.excursion.rate.ExcursionOffer;

import java.time.LocalDate;

@Entity
@Data
public class ExcursionDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "excursion_id", nullable = false)
    private Excursion excursion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rate_id")
    private ExcursionRate rate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private ExcursionOffer offer;





}
**/
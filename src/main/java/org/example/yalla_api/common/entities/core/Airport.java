package org.example.yalla_api.common.entities.core;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airportName;

    private String airportCode;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;


}

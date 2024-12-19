package org.example.yalla_api.common.entities.core;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(
        name = "hotel",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"area_id", "hotel_name"})
        })
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String hotelName;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;
}

package org.example.yalla_api.common.entities.transfer;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.image.Image;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double maxPax;
    private double minPax;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, optional = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = true)
    private Image image;

    private Boolean isActive = true;

}

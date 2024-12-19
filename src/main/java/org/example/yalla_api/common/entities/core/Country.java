package org.example.yalla_api.common.entities.core;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import org.example.yalla_api.common.entities.image.Image;
//import org.example.yalla_api.common.entities.image.Image;

@Entity
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false,unique = true)
    private String countryName;

    @Column(nullable = false,unique = true)
    private String countryCode;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, optional = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = true)
    private Image image;

}

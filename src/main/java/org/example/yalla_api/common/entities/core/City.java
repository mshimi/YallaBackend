package org.example.yalla_api.common.entities.core;


import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.image.Image;


@Entity
@Data
@Table(
        name = "city",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cityName","cityCode" ,"country_id"})
        })
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;

    private String cityCode;


    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "country_id")
    private Country country;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, optional = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = true)
    private Image image;

}

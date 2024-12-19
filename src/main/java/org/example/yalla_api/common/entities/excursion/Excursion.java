package org.example.yalla_api.common.entities.excursion;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;



    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<ExcursionDate> dates; // Excursion has multiple dates


}



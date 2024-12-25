package org.example.yalla_api.common.entities.excursion;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.enums.Language;

@Entity
@Data
public class CategoryTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Language lang;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}

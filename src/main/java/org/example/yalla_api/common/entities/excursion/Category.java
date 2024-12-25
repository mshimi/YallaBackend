package org.example.yalla_api.common.entities.excursion;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryTranslation> translations = new ArrayList<>();


    @PrePersist
    @PreUpdate
    private void validateTranslations() {
        if (translations == null || translations.isEmpty()) {
            throw new IllegalStateException("Each category must have at least one translation.");
        }
    }
}

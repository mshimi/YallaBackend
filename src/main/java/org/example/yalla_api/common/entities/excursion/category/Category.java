package org.example.yalla_api.common.entities.excursion.category;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.validations.MustHaveEnglishTranslation;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @MustHaveEnglishTranslation
    private List<CategoryTranslation> translations = new ArrayList<>();


    @PrePersist
    @PreUpdate
    private void validateTranslations() {
        if (translations == null || translations.isEmpty()) {
            throw new IllegalStateException("Each category must have at least one translation.");
        }
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;
}

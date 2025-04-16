package org.example.yalla_api.common.entities.excursion.category;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.enums.Language;
import org.example.yalla_api.common.validations.TranslationEntry;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
public class CategoryTranslation implements TranslationEntry {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryTranslation that)) return false;
        return lang == that.lang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lang);
    }


}

package org.example.yalla_api.common.entities.excursion.excursions;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.excursion.ExcursionTranslation;
import org.example.yalla_api.common.entities.excursion.category.Category;
import org.example.yalla_api.common.entities.excursion.excursionchildrenpolicy.ExcursionChildrenPolicy;
import org.example.yalla_api.common.entities.excursion.excursionitem.ExcursionItem;
import org.example.yalla_api.common.entities.excursion.highlight.Highlight;
import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.enums.Language;
import org.example.yalla_api.common.validations.MustHaveEnglishTranslation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // or TABLE_PER_CLASS, JOINED
@DiscriminatorColumn(name = "excursion_type")
public abstract class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("order ASC") // or "order DESC" if descending order is desired
    private List<Highlight> highlights;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @MustHaveEnglishTranslation
    private List<ExcursionTranslation> translations;




    @ManyToMany
    @JoinTable(
            name = "excursion_included_items",
            joinColumns = @JoinColumn(name = "excursion_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<ExcursionItem> includedItems;

    @ManyToMany
    @JoinTable(
            name = "excursion_excluded_items",
            joinColumns = @JoinColumn(name = "excursion_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<ExcursionItem> excludedItems;



    private boolean isActive = true;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "excursion_children_policy_id")
    private ExcursionChildrenPolicy excursionChildrenPolicy;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_area_id")
    private Area startArea;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Image> images;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_image_id")
    private Image mainImage;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_id")
    private Category mainCategory;

    @ManyToMany
    @JoinTable(
            name = "excursion_categories",
            joinColumns = @JoinColumn(name = "excursion_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @Column(name = "excursion_type", insertable = false, updatable = false)
    private String type;

    @ManyToMany
    @JoinTable(
            name = "excursion_bookable_from_areas",
            joinColumns = @JoinColumn(name = "excursion_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private List<Area> bookableFromAreas;


    public Optional<ExcursionTranslation> getTranslation(Language lang) {
        return translations.stream()
                .filter(t -> t.getLang() == lang)
                .findFirst();
    }


    @PrePersist
    @PreUpdate
    private void ensureMainCategoryInList() {
        if (mainCategory != null) {
            if (categories == null) {
                categories = new ArrayList<>();
            }
            if (!categories.contains(mainCategory)) {
                categories.add(mainCategory);
            }
        }
    }



}



package org.example.yalla_api.common.entities.excursion.excursionitem;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ExcursionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExcursionItemTranslation> translations;
}

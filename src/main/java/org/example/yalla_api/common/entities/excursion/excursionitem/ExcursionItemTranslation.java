package org.example.yalla_api.common.entities.excursion.excursionitem;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.enums.Language;

@Entity
@Data
public class ExcursionItemTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language lang;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ExcursionItem item;
}

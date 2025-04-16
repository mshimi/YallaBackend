package org.example.yalla_api.common.entities.excursion.highlight;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.excursion.excursions.Excursion;

import java.util.List;

@Entity
@Data
@Table(
        name = "highlight",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"excursion_id", "order"})
        }
)
public class Highlight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer order;

    @OneToMany(mappedBy = "highlight", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HighlightTranslation> translations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_id")
    private Excursion excursion;

}

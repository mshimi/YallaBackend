package org.example.yalla_api.common.entities.excursion.highlight;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.enums.Language;

@Entity
@Data
public class HighlightTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language lang;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "highlight_id")
    private Highlight highlight;
}
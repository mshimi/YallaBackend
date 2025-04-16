package org.example.yalla_api.common.entities.excursion;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.excursion.excursions.Excursion;
import org.example.yalla_api.common.enums.Language;
import org.example.yalla_api.common.validations.TranslationEntry;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class ExcursionTranslation implements TranslationEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
  private  Language lang;
   private String title;
   private String shortDescription;
   private String longDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_id")
    private Excursion excursion;

}
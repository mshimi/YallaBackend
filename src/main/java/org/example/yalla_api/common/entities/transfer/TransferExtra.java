package org.example.yalla_api.common.entities.transfer;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.image.Image;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
public class TransferExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double paxValue; // Pax equivalent value

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, optional = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = true)
    private Image image;

    @OneToMany(mappedBy = "transferExtra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferExtraTranslation> translations = new ArrayList<>();

    private Boolean isValid = true;

    @PrePersist
    @PreUpdate
    private void validateTranslations() {
        if (translations == null || translations.isEmpty()) {
            throw new IllegalStateException("Each TransferExtra must have at least one translation.");
        }
    }
}
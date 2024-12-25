package org.example.yalla_api.common.entities.transfer;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.excursion.Category;
import org.example.yalla_api.common.enums.Language;

@Entity
@Data
public class TransferExtraTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transfer_extra_id", nullable = false)
    @JsonBackReference
    private TransferExtra transferExtra;

    @Column(nullable = false)
    private Language lang;

    @Column(nullable = false)
    private String name;
}

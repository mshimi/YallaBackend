package org.example.yalla_api.common.entities.childrenPolicy;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("TRANSFERS")
public class TransferChildrenPolicy extends ChildrenPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}


package org.example.yalla_api.common.entities.childrenPolicy;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("EXCURSIONS")
public class ExcursionsChildrenPolicy extends ChildrenPolicy {


}
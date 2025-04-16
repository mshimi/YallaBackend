package org.example.yalla_api.common.entities.excursion.excursions;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("MULTI_DAY")
public class MultiDayExcursion extends Excursion {
    private Integer durationInDays; // Optional field
}
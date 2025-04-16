package org.example.yalla_api.common.entities.excursion.rate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("PERCENTAGE")
public class PercentageOffer extends ExcursionOffer {

    private double discountPercent; // e.g. 20.0 for 20%

    @Override
    public BigDecimal calculate(BigDecimal baseRate) {
        return baseRate.subtract(baseRate.multiply(BigDecimal.valueOf(discountPercent / 100)));
    }
}

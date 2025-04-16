package org.example.yalla_api.common.entities.excursion.rate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("FIXED")
public class FixedRateOffer extends ExcursionOffer {

    private BigDecimal fixedAmount;

    @Override
    public BigDecimal calculate(BigDecimal baseRate) {
        return fixedAmount;
    }
}
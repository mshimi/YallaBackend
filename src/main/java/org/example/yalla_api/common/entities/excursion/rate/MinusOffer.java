package org.example.yalla_api.common.entities.excursion.rate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("MINUS")
public class MinusOffer extends ExcursionOffer {

    private BigDecimal discountAmount;

    @Override
    public BigDecimal calculate(BigDecimal baseRate) {
        return baseRate.subtract(discountAmount).max(BigDecimal.ZERO);
    }
}

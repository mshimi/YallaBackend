package org.example.yalla_api.admin.dtos.transfer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgeRangesDTO {

    @NotNull(message = "ageFrom cannot be null")
    @Min(value = 0, message = "ageFrom must be a positive number")
    private Integer ageFrom;
    @NotNull(message = "ageTo cannot be null")
    @Min(value = 0, message = "ageTo must be a positive number")
    @Max(value = 18, message = "Max. child age is 18")
    private Integer ageTo ;
    private Double basePrice = 1.0;
    private Double paxValue = 1.0;
}

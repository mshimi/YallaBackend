package org.example.yalla_api.admin.dtos.transfer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddTransferRateDTO {
    @NotNull
    private Long sourceAreaId;
    @NotNull
    private Long destinationAreaId;

    @NotNull
    @Min(value = 1, message = "Transfer Rate can't be negative")
    private Double rate;

}

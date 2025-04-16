package org.example.yalla_api.common.dtos.core;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AirportDTO {

    private Long id;

    @NotBlank(message = "Airport Name is mandatory")
    private String airportName;

    @NotBlank(message = "Airport Code is mandatory")
    private String airportCode;

    @NotNull(message = "Area is mandatory")
    @Valid
    private AreaDTO area;
}

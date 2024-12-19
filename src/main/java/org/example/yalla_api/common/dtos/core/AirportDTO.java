package org.example.yalla_api.common.dtos.core;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AirportDTO {

    private Long id;

    @NotBlank(message = "Airport Name is mandatory")
    private String airportName;

    @NotBlank(message = "Airport Code is mandatory")
    private String airportCode;

    @NotBlank(message = "Area is mandatory")
    private AreaDTO area;
}

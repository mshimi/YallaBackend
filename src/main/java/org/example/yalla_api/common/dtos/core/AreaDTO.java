package org.example.yalla_api.common.dtos.core;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AreaDTO {

    private Long id;

    @NotBlank(message = "Area Name is mandatory")
    private String areaName;


@NotNull(message = "City is mandatory")
@Valid
    private CityDTO city;
}

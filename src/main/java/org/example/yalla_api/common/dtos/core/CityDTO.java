package org.example.yalla_api.common.dtos.core;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CityDTO {

    private Long id;

    @NotBlank(message = "City Name is mandatory")
    private String cityName;
    @NotBlank(message = "City Code is mandatory")
    private String cityCode;
    @NotBlank(message = "Country is mandatory")
    @NotNull(message = "Country can't be null")
    @Valid
    private CountryDTO country;

    private Long imageId;

}

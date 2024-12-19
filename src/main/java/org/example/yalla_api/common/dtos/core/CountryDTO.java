package org.example.yalla_api.common.dtos.core;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CountryDTO {

    private Long id;              // Include this only for response, may be ignored in the request

    @NotBlank(message = "Country name is mandatory")
    private String countryName;
    @NotBlank(message = "Country code is mandatory")
    private String countryCode;

    private Long imageId;

}

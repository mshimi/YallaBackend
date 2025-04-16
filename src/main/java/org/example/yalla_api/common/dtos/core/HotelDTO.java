package org.example.yalla_api.common.dtos.core;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class HotelDTO {

    private Long id;

    @NotBlank(message = "Hotel Name is mandatory")
    private String hotelName;

    @NotNull(message = "Area is mandatory")
    @Valid
    private AreaDTO area;
}

package org.example.yalla_api.common.dtos.transfer;

import lombok.Data;

@Data
public class VehicleDTO {

    private Long id;
    private double maxPax;
    private double minPax;

    private String name;

    private Long imageId;

}

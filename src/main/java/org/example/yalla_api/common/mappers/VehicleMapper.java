package org.example.yalla_api.common.mappers;

import org.example.yalla_api.common.dtos.transfer.VehicleDTO;
import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "image.id", target = "imageId")
    VehicleDTO toDTO(Vehicle vehicle);


    Vehicle toEntity(VehicleDTO vehicleDTO);


}

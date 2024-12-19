package org.example.yalla_api.common.mappers;

import org.example.yalla_api.common.dtos.core.AreaDTO;
import org.example.yalla_api.common.dtos.core.CityDTO;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AreaMapper {

    @Mapping(source = "city.country.image.id", target = "city.country.imageId") // Map the Image ID to imageId in CountryDTO
    @Mapping(source = "city.image.id", target = "city.imageId")
    AreaDTO toDTO(Area area);


    Area toEntity(AreaDTO cityDTO);


}

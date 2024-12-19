package org.example.yalla_api.common.mappers;

import org.example.yalla_api.common.dtos.core.CityDTO;
import org.example.yalla_api.common.entities.core.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "country.image.id", target = "country.imageId") // Map the Image ID to imageId in CountryDTO
    @Mapping(source = "image.id", target = "imageId")
    CityDTO toDTO(City city);


    City toEntity(CityDTO cityDTO);


}

package org.example.yalla_api.common.mappers;

import org.example.yalla_api.common.dtos.core.CountryDTO;
import org.example.yalla_api.common.entities.core.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {

   @Mapping(source = "image.id", target = "imageId") // Map the Image ID to imageId in CountryDTO
    CountryDTO toDTO(Country country);


    Country toEntity(CountryDTO countryDTO);
}
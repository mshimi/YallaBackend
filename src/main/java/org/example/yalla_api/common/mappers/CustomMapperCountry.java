package org.example.yalla_api.common.mappers;


import org.example.yalla_api.common.dtos.core.CountryDTO;
import org.example.yalla_api.common.entities.core.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomMapperCountry {




@Mapping(source = "image.id", target = "imageId") // Map the Image ID to imageId in CountryDTO

        // Maps Country to CountryDTO for the response
        CountryDTO toDTO(Country country);


        Country toEntity(CountryDTO countryDTO);

}

package org.example.yalla_api.common.mappers;


import org.example.yalla_api.common.dtos.core.HotelDTO;
import org.example.yalla_api.common.entities.core.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(source = "area.city.country.image.id", target = "area.city.country.imageId") // Map the Image ID to imageId in CountryDTO
    @Mapping(source = "area.city.image.id", target = "area.city.imageId")
    HotelDTO toDTO(Hotel hotel);


    Hotel toEntity(HotelDTO hotelDTO);


}

package org.example.yalla_api.admin.mappers.transfer;

import org.example.yalla_api.admin.dtos.transfer.AgeRangesDTO;
import org.example.yalla_api.common.entities.childrenPolicy.AgeRange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgeRangeMapper {



    AgeRangesDTO toDto(AgeRange source);

    @Mapping(target = "basePrice", source = "basePrice", defaultValue = "1.0")
    @Mapping(target = "paxValue", source = "paxValue", defaultValue = "1.0")
    AgeRange toSource (AgeRangesDTO dto);

}

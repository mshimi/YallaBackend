package org.example.yalla_api.admin.mappers.transfer;

import org.example.yalla_api.admin.dtos.transfer.TransferExtraDTO;
import org.example.yalla_api.common.entities.transfer.TransferExtra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferExtraMapper {

    @Mapping(source = "image.id", target = "imageId")
    TransferExtraDTO toDTO(TransferExtra source);


    TransferExtra toEntity(TransferExtraDTO dto);

}





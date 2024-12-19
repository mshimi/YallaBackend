package org.example.yalla_api.common.mappers;

import org.example.yalla_api.admin.dtos.transfer.TransferRateDTO;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TransferRateMapper {

    TransferRate toEntity (TransferRateDTO dto);

    TransferRateDTO toDto (TransferRate entity);
}

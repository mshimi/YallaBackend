package org.example.yalla_api.admin.mappers.transfer;

import org.example.yalla_api.admin.dtos.transfer.ReleasePeriodDTO;
import org.example.yalla_api.common.entities.Release.ReleasePeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferReleaseMapper {

    ReleasePeriodDTO toDTO(ReleasePeriod release);


    ReleasePeriod toEntity(ReleasePeriodDTO releaseDTO);

}

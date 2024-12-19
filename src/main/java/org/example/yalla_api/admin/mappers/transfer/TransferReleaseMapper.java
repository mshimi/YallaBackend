package org.example.yalla_api.admin.mappers.transfer;

import org.example.yalla_api.admin.dtos.transfer.ReleasePeriodDTO;
import org.example.yalla_api.common.dtos.core.AirportDTO;
import org.example.yalla_api.common.entities.Release.ReleasePeriod;
import org.example.yalla_api.common.entities.core.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferReleaseMapper {

    ReleasePeriodDTO toDTO(ReleasePeriod release);


    ReleasePeriod toEntity(ReleasePeriodDTO releaseDTO);

}

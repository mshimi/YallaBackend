package org.example.yalla_api.admin.mappers.transfer;

import org.example.yalla_api.admin.dtos.transfer.TestBookingResponse;
import org.example.yalla_api.admin.dtos.transfer.TestBookingResponseDTO;
import org.example.yalla_api.admin.dtos.transfer.TransferRateDTO;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.mappers.VehicleMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TransferPriceMapper.class, VehicleMapper.class})
public interface TestBookingResponseMapper {


     TestBookingResponseDTO toDto (TestBookingResponse dto);


}

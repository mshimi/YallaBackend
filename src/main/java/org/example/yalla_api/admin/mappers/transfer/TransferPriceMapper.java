package org.example.yalla_api.admin.mappers.transfer;

import org.example.yalla_api.admin.dtos.transfer.TransferPriceDTO;
import org.example.yalla_api.common.logic.transfer.pricecalculator.TransferPrice;
import org.example.yalla_api.common.mappers.VehicleMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = VehicleMapper.class)
public interface TransferPriceMapper {

    TransferPriceDTO toDTO(TransferPrice entity);

    List<TransferPriceDTO> toDTOs(List<TransferPrice> entities);
}

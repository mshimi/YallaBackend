package org.example.yalla_api.admin.mappers.transfer;

import org.example.yalla_api.admin.dtos.transfer.TransferChildrenPolicyDTO;
import org.example.yalla_api.common.entities.childrenPolicy.TransferChildrenPolicy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferChildrenPolicyMapper {


    TransferChildrenPolicy toSource(TransferChildrenPolicyDTO dto);


    TransferChildrenPolicyDTO toDto(TransferChildrenPolicy source);


}

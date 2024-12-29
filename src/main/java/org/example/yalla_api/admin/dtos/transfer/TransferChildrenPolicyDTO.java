package org.example.yalla_api.admin.dtos.transfer;

import lombok.Data;

import java.util.List;

@Data
public class TransferChildrenPolicyDTO {



    private Long id;
    private List<AgeRangesDTO> ageRanges;
}

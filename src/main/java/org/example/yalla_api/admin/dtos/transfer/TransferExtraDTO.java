package org.example.yalla_api.admin.dtos.transfer;

import lombok.Data;

import java.util.List;

@Data
public class TransferExtraDTO {

    private Long id;

    private Double paxValue; // Pax equivalent value

    private List<TransferExtraTranslationDTO> translations;

    private Long imageId;


}

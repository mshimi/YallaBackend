package org.example.yalla_api.admin.dtos.transfer;

import lombok.Data;
import org.example.yalla_api.common.enums.Language;

@Data
public  class TransferExtraTranslationDTO {
    private Language lang;
    private String name;
}
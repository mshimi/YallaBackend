package org.example.yalla_api.admin.dtos.transfer;

import lombok.Data;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.logic.transfer.childrenpolicy.EvaluatedChildren;
import org.example.yalla_api.common.logic.transfer.extras.TransferExtras;
import org.example.yalla_api.common.logic.transfer.pricecalculator.TransferPrice;

import java.util.List;

@Data
public class TestBookingResponseDTO {


    private TransferExtraDTO extras;
    private List<TransferPriceDTO> transferPrices;
    private EvaluatedChildren evaluatedChildren;
    private TransferRateDTO rate;

}

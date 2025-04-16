package org.example.yalla_api.admin.dtos.transfer;

import lombok.Builder;
import lombok.Data;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.logic.transfer.childrenpolicy.EvaluatedChildren;
import org.example.yalla_api.common.logic.transfer.extras.TransferExtraItem;
import org.example.yalla_api.common.logic.transfer.extras.TransferExtras;
import org.example.yalla_api.common.logic.transfer.pricecalculator.TransferPrice;

import java.util.List;

@Builder
@Data
public class TestBookingResponse {


    private TransferExtras extras;
    private List<TransferPrice> transferPrices;
    private EvaluatedChildren evaluatedChildren;
    private TransferRate rate;
}

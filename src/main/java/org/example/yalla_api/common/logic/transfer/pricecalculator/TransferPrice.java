package org.example.yalla_api.common.logic.transfer.pricecalculator;

import lombok.Builder;
import lombok.Data;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.entities.transfer.Vehicle;

@Data
@Builder
public class TransferPrice {

    Vehicle vehicle;
 //   TransferRate transferRate;
    Double totalRates;


}
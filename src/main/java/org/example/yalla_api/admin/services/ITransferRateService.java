package org.example.yalla_api.admin.services;

import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.transfer.TransferRate;

import java.util.List;
import java.util.Map;

public interface ITransferRateService {


    TransferRate addTransferRate(Long sourceArea, Long destinationArea, Double rate);

    TransferRate addTransferRate(Area sourceArea, Area destinationArea, Double rate);

    List<TransferRate> getActiveTransferRates( Map<String, String> filters);

    TransferRate getTransferRateForAreas(Long sourceArea,Long destinationArea);

    TransferRate getTransferRateForAreas(Area sourceArea,Area destinationArea);

    List<TransferRate> addTransferRates (List<TransferRate> rates);


    void setTransferRateInactive(Long id);

}

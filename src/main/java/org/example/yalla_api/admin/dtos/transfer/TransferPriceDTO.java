package org.example.yalla_api.admin.dtos.transfer;

import lombok.Data;
import org.example.yalla_api.common.dtos.transfer.VehicleDTO;
import org.example.yalla_api.common.entities.transfer.Vehicle;

@Data
public class TransferPriceDTO {

    VehicleDTO vehicle;
    //   TransferRate transferRate;
    Double totalRates;
}

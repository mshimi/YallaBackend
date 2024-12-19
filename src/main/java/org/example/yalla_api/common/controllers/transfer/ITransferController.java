package org.example.yalla_api.common.controllers.transfer;

import org.example.yalla_api.common.entities.transfer.Vehicle;

public interface ITransferController {



 public    Vehicle selectCheapestVehicleByPaxNumber(Integer paxNumber);

}

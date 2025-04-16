package org.example.yalla_api.common.logic.transfer.vehicle;

import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.example.yalla_api.common.services.transfer.VehicleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PassingVehicleSelector {


    private final VehicleService vehicleService;

    public PassingVehicleSelector (
            VehicleService vehicleService
    ){
        this.vehicleService = vehicleService;
    }



    public List<Vehicle> getPassingVehicles(double paxValue){
       return vehicleService.getTopTwoVehiclesByPaxNumber(paxValue);
    }

}

package org.example.yalla_api.admin.services;

import org.example.yalla_api.admin.dtos.transfer.TestBookingResponse;

import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.example.yalla_api.common.logic.transfer.childrenpolicy.ChildPolicyEvaluator;
import org.example.yalla_api.common.logic.transfer.childrenpolicy.EvaluatedChildren;
import org.example.yalla_api.common.logic.transfer.extras.TransferExtraCalculator;
import org.example.yalla_api.common.logic.transfer.extras.TransferExtras;
import org.example.yalla_api.common.logic.transfer.pricecalculator.TransferPrice;
import org.example.yalla_api.common.logic.transfer.pricecalculator.TransferPriceCalculator;

import org.example.yalla_api.common.logic.transfer.vehicle.PassingVehicleSelector;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class TransferTestBookingService {


    private final TransferExtraCalculator extraCalculator;
    private final ChildPolicyEvaluator childPolicyEvaluator;
    private final TransferPriceCalculator transferPriceCalculator;
    private final PassingVehicleSelector passingVehicleSelector;

    public TransferTestBookingService(
            TransferExtraCalculator transferExtraCalculator,
            ChildPolicyEvaluator childPolicyEvaluator,
            TransferPriceCalculator transferPriceCalculator,
            PassingVehicleSelector passingVehicleSelector

    ) {


        this.extraCalculator = transferExtraCalculator;
        this.childPolicyEvaluator = childPolicyEvaluator;
        this.transferPriceCalculator = transferPriceCalculator;
        this.passingVehicleSelector = passingVehicleSelector;

    }



    public TestBookingResponse testBooking(
            Long startAreaId, Long endAreaId,
            Integer adults, List<Integer> child, LocalDate startDate, Map<Integer,Long> extras
    ) {

     TransferExtras extraItems = extraCalculator.calculateItems(extras);
     EvaluatedChildren evaluatedChildren = childPolicyEvaluator.evaluate(child);

     double totalPaxValue = getTotalPaxValue(extraItems.getTotalPaxValue(),evaluatedChildren.getTotalPaxValue(),adults);

     double totalPaxPrice = getTotalPaxValue(extraItems.getTotalPaxValue(),evaluatedChildren.getTotalPrice(),adults);

     TransferRate transferRate = transferPriceCalculator.getTransferRateForAreas(startDate,startAreaId,endAreaId);


     List<Vehicle> vehicles = passingVehicleSelector.getPassingVehicles(totalPaxValue);


   List<TransferPrice>  transferPrices = transferPriceCalculator.calculateTransferPrice(transferRate,totalPaxPrice,vehicles  );

        return TestBookingResponse.builder()
                .extras(extraItems)
                .transferPrices(transferPrices)
                .evaluatedChildren(evaluatedChildren)
                .rate(transferRate)
                .build();
    }



    private double getTotalPaxValue(double extras, double child, double adults) {
        return extras + child + adults;
    }


    private double getTotalPaxPrice(double extras, double child, double adults) {
        return extras + child + adults;
    }

}



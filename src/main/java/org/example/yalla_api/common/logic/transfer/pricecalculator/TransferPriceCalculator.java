package org.example.yalla_api.common.logic.transfer.pricecalculator;

import org.example.yalla_api.admin.controllers.transfer.TransferRateController;
import org.example.yalla_api.admin.services.TransferRateService;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.example.yalla_api.common.logic.transfer.release.ReleaseEvaluator;
import org.example.yalla_api.common.services.transfer.TransferReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransferPriceCalculator {

    private final TransferRateService transferRateService;
    private final ReleaseEvaluator releaseEvaluator;

    public TransferPriceCalculator(
            ReleaseEvaluator releaseEvaluator,
            TransferRateService transferRateService
    ) {
        this.transferRateService = transferRateService;
        this.releaseEvaluator = releaseEvaluator;
    }


    private static final Logger logger = LoggerFactory.getLogger(TransferPriceCalculator.class);

    public TransferRate getTransferRateForAreas(LocalDate startDate, Long startArea, Long endArea){

        releaseEvaluator.assertBookingAllowed(startDate);

       TransferRate transferRate = transferRateService.getTransferRateForAreas(startArea, endArea);

        logger.info("TransferRate: {}", transferRate);
        assertTransferRateRelease(transferRate.getRelease(),startDate);


        return transferRate;
    }



    private void assertTransferRateRelease(Integer releaseDays, LocalDate startDate){

        LocalDate bookingDate = LocalDate.now();
        LocalDate earliestAllowedDate = bookingDate.plusDays(releaseDays);


        if( startDate.isBefore(earliestAllowedDate)){
            throw new IllegalArgumentException("Booking is not allowed. Minimum release days not met.");
        }

    }


    public List<TransferPrice> calculateTransferPrice(TransferRate transferRate, double totalPaxPrice, List<Vehicle> vehicles) {

        List<TransferPrice> transferPrices = new ArrayList<>();
    for (Vehicle vehicle : vehicles) {
     var transferPrice =  calculateTransferPriceForVehicle(vehicle,totalPaxPrice,transferRate);
     transferPrices.add(transferPrice);
    }

    return transferPrices;
    }


    public TransferPrice calculateTransferPriceForVehicle(Vehicle vehicle, double totalPaxPrice, TransferRate transferRate) {

      Double paxValue = Math.max(vehicle.getMinPax(), totalPaxPrice);

      Double ratePerPerson = transferRate.getRatePerPerson();

      Double totalPrice = paxValue * ratePerPerson;

      return TransferPrice
              .builder()
              .vehicle(vehicle)

              .totalRates(totalPrice)
              .build();

    }
}



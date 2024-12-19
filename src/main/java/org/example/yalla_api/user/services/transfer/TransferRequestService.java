package org.example.yalla_api.user.services.transfer;

import jakarta.transaction.Transactional;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.transfer.TransferClientRequest;
import org.example.yalla_api.common.entities.transfer.TransferExtra;
import org.example.yalla_api.common.entities.transfer.TransferResponse;
import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.example.yalla_api.common.repositories.transfer.TransferClientRequestRepository;
import org.example.yalla_api.common.repositories.transfer.TransferRateRepository;
import org.example.yalla_api.common.repositories.transfer.TransferResponseRepository;
import org.example.yalla_api.common.repositories.transfer.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferRequestService {

    @Autowired
    private BookingValidationService bookingValidationService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TransferRateRepository transferRateRepository;

    @Autowired
    private TransferClientRequestRepository transferClientRequestRepository;

    @Autowired
    private TransferResponseRepository transferResponseRepository;

    @Autowired
    private ChildrenPolicyService childrenPolicyService;

    /**
     * Handles the client's transfer request.
     *
     * @param request The transfer client request.
     * @return The transfer response.
     */
    @Transactional
    public TransferResponse handleTransferRequest(TransferClientRequest request) {
        // Validate release conditions
        validateReleaseConditions(request.getRequestDate());

        // Calculate total pax
        double totalPax = calculateTotalPax(request.getNumberOfAdults(), request.getChildrenAges(), request.getExtras());

        // Get the appropriate vehicle for the pax count
        Vehicle vehicle = findSuitableVehicle(totalPax);



        // Get the transfer rate between the two areas
        double transferRate = findTransferRate(request.getStartArea(), request.getEndArea());

        // Calculate total price
        double totalPrice = transferRate * totalPax;

        // Save the request and response
        TransferClientRequest savedRequest = transferClientRequestRepository.save(request);

        TransferResponse response = new TransferResponse();
        response.setRequest(savedRequest);
        response.setVehicle(vehicle);
        response.setTotalPrice(totalPrice);
        return transferResponseRepository.save(response);
    }

    /**
     * Validates release conditions for the given date.
     *
     * @param requestDate The request date.
     */
    private void validateReleaseConditions(LocalDate requestDate) {
        if (!bookingValidationService.isBookingAllowed(requestDate)) {
            throw new IllegalArgumentException("Booking not allowed due to release conditions.");
        }
    }

    /**
     * Calculates the total pax based on adults, children, and extras.
     *
     * @param adults      Number of adults.
     * @param childrenAges List of children ages.
     * @param extras      List of extras.
     * @return The total pax.
     */
    private double calculateTotalPax(int adults, List<Integer> childrenAges, List<TransferExtra> extras) {
        double totalPax = adults;

        // Calculate pax from children using policy
        totalPax += childrenPolicyService.calculatePaxFromChildren(childrenAges);

        // Add pax equivalent from extras
        if (extras != null) {
            for (TransferExtra extra : extras) {
                totalPax += extra.getPaxValue();
            }
        }

        return totalPax;
    }

    /**
     * Finds a suitable vehicle for the given pax count.
     *
     * @param totalPax The total pax count.
     * @return The suitable vehicle.
     */
    private Vehicle findSuitableVehicle(double totalPax) {
        return vehicleRepository.findVehicleByPaxCapacity(totalPax)
                .orElseThrow(() -> new IllegalArgumentException("No suitable vehicle found for pax count: " + totalPax));
    }

    /**
     * Finds the transfer rate between two areas.
     *
     * @param startArea The start area.
     * @param endArea   The end area.
     * @return The transfer rate.
     */
    private double findTransferRate(Area startArea, Area endArea) {
        return transferRateRepository.findRateByAreas(startArea.getId(), endArea.getId())
                .orElseThrow(() -> new IllegalArgumentException("Transfer rate not found between areas: " + startArea + " and " + endArea));
    }
}
package org.example.yalla_api.common.logic.transfer.release;

import org.example.yalla_api.common.services.transfer.TransferReleaseService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReleaseEvaluator {



  private   final TransferReleaseService transferReleaseService;

   public ReleaseEvaluator(TransferReleaseService transferReleaseService) {
       this.transferReleaseService = transferReleaseService;
   }

    public boolean isBookingAllowed(LocalDate travelDate) {
        Integer releaseDays = transferReleaseService.findReleaseDaysForTravelDate(travelDate);

        LocalDate bookingDate = LocalDate.now();
        LocalDate earliestAllowedDate = bookingDate.plusDays(releaseDays);

        return !travelDate.isBefore(earliestAllowedDate); // travelDate >= today + releaseDays
    }



    public void assertBookingAllowed(LocalDate travelDate) {
        if (!isBookingAllowed(travelDate)) {
            throw new IllegalArgumentException("Booking is not allowed.1 Minimum release days not met.");
        }
    }
}
package org.example.yalla_api.user.services.transfer;

import org.example.yalla_api.common.entities.Release.ReleasePeriod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingValidationService {
//    @Autowired
//    private ReleasePeriodService releasePeriodService;

    public boolean isBookingAllowed(LocalDate bookingDate) {
        // Get the applicable release period
//        ReleasePeriod releasePeriod = releasePeriodService.getApplicableRelease(bookingDate);

//         LocalDate currentDate = LocalDate.now();
//        // Validate against the release period
//        if (releasePeriod != null) {
//            return !currentDate.isAfter(bookingDate.minusDays(releasePeriod.getReleaseDays()));
//        }

        // If no release applies, booking is not allowed
        return false;
    }
}
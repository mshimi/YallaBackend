package org.example.yalla_api.common.services.transfer;

import org.example.yalla_api.common.entities.Release.ReleasePeriod;
import org.example.yalla_api.common.repositories.releasePeriod.ReleasePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TransferReleaseService {

    @Autowired
    private ReleasePeriodRepository releasePeriodRepository;


    public ReleasePeriod getGeneralReleasePeriod() {
        return releasePeriodRepository.findGeneralRelease().orElse(null);
    }


    public ReleasePeriod addGeneralRelease(ReleasePeriod releasePeriod) {
        validateGeneralReleasePeriod(releasePeriod);
        ReleasePeriod generalRelease = getGeneralReleasePeriod();
        if (generalRelease != null) {
            generalRelease.setIsActive(false);
            releasePeriodRepository.save(generalRelease);
        }
        ReleasePeriod newGeneralRelease = new ReleasePeriod();
        newGeneralRelease.setIsGeneral(true);
        newGeneralRelease.setReleaseDays(releasePeriod.getReleaseDays());
        newGeneralRelease.setEndDate(null);
        newGeneralRelease.setStartDate(null);

        return releasePeriodRepository.save(newGeneralRelease);

    }


    public List<ReleasePeriod> getActiveReleasePeriod() {
        return releasePeriodRepository.findActivePeriods();
    }

    public void setReleaseAsInActive(Long id) {
        ReleasePeriod release = releasePeriodRepository.findById(id).orElseThrow();

        if (release.getIsGeneral()) {
            throw new IllegalArgumentException("General Release can be inactive");
        }

        release.setIsActive(false);
        releasePeriodRepository.save(release);
    }


    /**
     * Add a new release period and handle overlapping periods.
     *
     * @param newRelease The new release period to add.
     * @return The saved new release.
     */
    @Transactional
    public ReleasePeriod addReleasePeriod(ReleasePeriod newRelease) {
        validateReleasePeriod(newRelease);

        // Fetch all active releases that overlap with the new release
        List<ReleasePeriod> overlappingReleases = releasePeriodRepository.findOverlappingReleases(
                newRelease.getStartDate(), newRelease.getEndDate()
        );

        // Adjust and deactivate overlapping releases
        List<ReleasePeriod> adjustedReleases = adjustOverlappingReleases(overlappingReleases, newRelease);

        // Save adjusted releases
        releasePeriodRepository.saveAll(adjustedReleases);

        // Save the new release
        newRelease.setIsActive(true);
        return releasePeriodRepository.save(newRelease);
    }




    public List<ReleasePeriod> backToGeneralRelease(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date, and neither can be null.");
        }

        // Fetch all active releases overlapping with the given date range
        List<ReleasePeriod> overlappingReleases = releasePeriodRepository.findActiveReleasesWithinDates(startDate, endDate);

        List<ReleasePeriod> adjustedReleases = new ArrayList<>();

        for (ReleasePeriod existingRelease : overlappingReleases) {
            // Deactivate the existing release
            existingRelease.setIsActive(false);

            // Adjust releases that start before the specified startDate
            if (existingRelease.getStartDate().isBefore(startDate)) {
                ReleasePeriod beforeRangeRelease = new ReleasePeriod();
                beforeRangeRelease.setStartDate(existingRelease.getStartDate());
                beforeRangeRelease.setEndDate(startDate.minusDays(1));
                beforeRangeRelease.setReleaseDays(existingRelease.getReleaseDays());
                beforeRangeRelease.setIsActive(true);
                adjustedReleases.add(beforeRangeRelease);
            }

            // Adjust releases that end after the specified endDate
            if (existingRelease.getEndDate().isAfter(endDate)) {
                ReleasePeriod afterRangeRelease = new ReleasePeriod();
                afterRangeRelease.setStartDate(endDate.plusDays(1));
                afterRangeRelease.setEndDate(existingRelease.getEndDate());
                afterRangeRelease.setReleaseDays(existingRelease.getReleaseDays());
                afterRangeRelease.setIsActive(true);
                adjustedReleases.add(afterRangeRelease);
            }
        }

        // Save deactivated releases
        releasePeriodRepository.saveAll(overlappingReleases);

        // Save adjusted releases
      return  releasePeriodRepository.saveAll(adjustedReleases);
    }


    /**
     * Adjust existing releases to avoid overlapping with the new release.
     *
     * @param overlappingReleases The existing releases that overlap.
     * @param newRelease          The new release period.
     * @return A list of adjusted releases to be saved.
     */
    private List<ReleasePeriod> adjustOverlappingReleases(List<ReleasePeriod> overlappingReleases, ReleasePeriod newRelease) {
        List<ReleasePeriod> adjustedReleases = new ArrayList<>();

        for (ReleasePeriod existingRelease : overlappingReleases) {
            // Deactivate the existing release
            existingRelease.setIsActive(false);

            // Split the existing release if necessary
            if (existingRelease.getStartDate().isBefore(newRelease.getStartDate())) {
                ReleasePeriod beforeNewRelease = new ReleasePeriod();
                beforeNewRelease.setStartDate(existingRelease.getStartDate());
                beforeNewRelease.setEndDate(newRelease.getStartDate().minusDays(1));
                beforeNewRelease.setReleaseDays(existingRelease.getReleaseDays());
                beforeNewRelease.setIsActive(true);
                adjustedReleases.add(beforeNewRelease);
            }

            if (existingRelease.getEndDate().isAfter(newRelease.getEndDate())) {
                ReleasePeriod afterNewRelease = new ReleasePeriod();
                afterNewRelease.setStartDate(newRelease.getEndDate().plusDays(1));
                afterNewRelease.setEndDate(existingRelease.getEndDate());
                afterNewRelease.setReleaseDays(existingRelease.getReleaseDays());
                afterNewRelease.setIsActive(true);
                adjustedReleases.add(afterNewRelease);
            }
        }

        return adjustedReleases;
    }

    /**
     * Validate the release period for logical consistency.
     *
     * @param releasePeriod The release period to validate.
     */
    private void validateReleasePeriod(ReleasePeriod releasePeriod) {
        if (releasePeriod.getReleaseDays() == null || releasePeriod.getReleaseDays() < 0) {
            throw new IllegalArgumentException("Release days must be a non-negative integer.");
        }
        if (releasePeriod.getStartDate() != null && releasePeriod.getEndDate() != null &&
                releasePeriod.getStartDate().isAfter(releasePeriod.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before or equal to the end date.");
        }
    }


    /**
     * Validate the release period for logical consistency.
     *
     * @param releasePeriod The release period to validate.
     */
    private void validateGeneralReleasePeriod(ReleasePeriod releasePeriod) {
        if (releasePeriod.getReleaseDays() == null || releasePeriod.getReleaseDays() < 0) {
            throw new IllegalArgumentException("Release days must be a non-negative integer.");
        }
        if (releasePeriod.getStartDate() != null || releasePeriod.getEndDate() != null) {
            throw new IllegalArgumentException("invalid General Release");
        }
    }





    public Integer findReleaseDaysForTravelDate(LocalDate travelDate) {
      List<ReleasePeriod> releasePeriods =  releasePeriodRepository.getReleaseForTravelDate(travelDate);

      if(releasePeriods.isEmpty()){
        ReleasePeriod generalRelease =  getGeneralReleasePeriod();
        if(generalRelease != null){
            return generalRelease.getReleaseDays();
        }
        return 0;
      }
      return releasePeriods.stream().max(Comparator.comparing(ReleasePeriod::getReleaseDays)).get().getReleaseDays();
    }



}

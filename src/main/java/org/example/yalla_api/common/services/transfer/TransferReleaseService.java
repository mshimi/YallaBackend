package org.example.yalla_api.common.services.transfer;

import org.example.yalla_api.common.entities.Release.ReleasePeriod;
import org.example.yalla_api.common.repositories.releasePeriod.ReleasePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransferReleaseService {

    @Autowired
    private ReleasePeriodRepository releasePeriodRepository;

    
    
    public ReleasePeriod getGeneralReleasePeriod (){
        return releasePeriodRepository.findGeneralRelease().orElse(null);
    }


    public List<ReleasePeriod> getActiveReleasePeriod (){
        return releasePeriodRepository.findActivePeriods();
    }

    public void setReleaseAsInActive(Long id){
        ReleasePeriod release =  releasePeriodRepository.findById(id).orElseThrow();
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
}

package org.example.yalla_api.common.repositories.releasePeriod;

import org.example.yalla_api.common.entities.Release.ReleasePeriod;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReleasePeriodRepository extends BaseRepository<ReleasePeriod, Long> {

    // Fetch all active release periods
    @Query("SELECT r FROM ReleasePeriod r WHERE r.isActive = true AND r.isGeneral = false AND r.endDate > CURRENT_DATE ORDER BY r.startDate ASC")
    List<ReleasePeriod> findActivePeriods();

    @Query("SELECT r FROM ReleasePeriod r WHERE r.isGeneral = false AND  r.endDate > CURRENT_DATE ORDER BY r.startDate ASC")
    List<ReleasePeriod> findAllPeriods();

    // Fetch the general release (if exists)
    @Query("SELECT r FROM ReleasePeriod r WHERE r.isGeneral = true AND r.isActive = true")
    Optional<ReleasePeriod> findGeneralRelease();

    /**
     * Find all active releases that overlap with a given period.
     *
     * @param startDate The start date of the new release.
     * @param endDate   The end date of the new release.
     * @return A list of overlapping active releases.
     */
    @Query("SELECT r FROM ReleasePeriod r WHERE r.isActive = true " +
            "AND (r.startDate <= :endDate AND r.endDate >= :startDate)")
    List<ReleasePeriod> findOverlappingReleases(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
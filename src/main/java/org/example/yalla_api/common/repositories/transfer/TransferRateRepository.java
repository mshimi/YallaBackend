package org.example.yalla_api.common.repositories.transfer;

import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferRateRepository extends JpaRepository<TransferRate, Long> {
    @Query("SELECT r.ratePerPerson FROM TransferRate r WHERE r.sourceArea.id = :startArea AND r.destinationArea.id = :endArea AND r.isActive = true")
    Optional<Double> findRateByAreas(@Param("startArea") Long startArea, @Param("endArea") Long endArea);
}
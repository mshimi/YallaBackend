package org.example.yalla_api.common.repositories.transfer;

import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRateRepository extends JpaRepository<TransferRate, Long>, JpaSpecificationExecutor<TransferRate> {
    @Query("SELECT r.ratePerPerson FROM TransferRate r WHERE r.sourceArea.id = :startArea AND r.destinationArea.id = :endArea AND r.isActive = true")
    Optional<Double> findRateByAreas(@Param("startArea") Long startArea, @Param("endArea") Long endArea);

    Page<TransferRate> findAllByIsActiveIsTrue(Pageable pageable);


    List<TransferRate> findBySourceAreaIdAndDestinationAreaIdAndIsActiveTrue(Long sourceAreaId, Long destinationAreaId);

    Optional<TransferRate> findBySourceAreaAndDestinationAreaAndIsActiveTrue(Area sourceArea, Area destinationArea);



    @Query("SELECT r from TransferRate r where r.destinationArea.id =:destinationAreaId AND " +
            "r.sourceArea.id = :sourceAreaId AND r.isActive = true")
    Optional<TransferRate>   findActiveTransferRateForAreas (Long sourceAreaId, Long destinationAreaId);

}
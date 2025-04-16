package org.example.yalla_api.common.repositories.transfer;

import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends BaseRepository<Vehicle, Long> {
    @Query("SELECT v FROM Vehicle v WHERE v.minPax <= :pax AND v.maxPax >= :pax")
    Optional<Vehicle> findVehicleByPaxCapacity(@Param("pax") double pax);


    @Query("SELECT v FROM Vehicle v WHERE  v.maxPax >= :pax ORDER BY v.maxPax ASC")
    List<Vehicle> findTop2ByMinPaxLessThanEqual(@Param("pax") double pax, Pageable pageable);


}

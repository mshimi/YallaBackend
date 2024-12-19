package org.example.yalla_api.common.repositories.core;

import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends BaseRepository<City,Long> {

    List<City> findAllCitiesByCountryId(Long countryId);

    Page<City> findAllCitiesByCountryId(Long countryId, Pageable pageable);
}

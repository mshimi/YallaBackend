package org.example.yalla_api.common.repositories.core;

import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends BaseRepository<Area,Long> {

    List<Area> findAllAreaByCityId(Long countryId);

    Page<Area> findAllAreasByCityId(Long countryId, Pageable pageable);
}

package org.example.yalla_api.common.repositories.core;

import org.example.yalla_api.common.entities.core.Airport;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends BaseRepository<Airport,Long> {


}

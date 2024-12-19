package org.example.yalla_api.common.services.core;

import org.example.yalla_api.common.entities.core.Airport;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.repositories.core.AirportRepository;
import org.example.yalla_api.common.repositories.core.AreaRepository;
import org.example.yalla_api.common.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService extends BaseService<Airport,Long> {


    @Autowired
   private AirportRepository airportRepository;




    @Override
    protected BaseRepository<Airport, Long> getRepository() {
        return airportRepository;
    }




}

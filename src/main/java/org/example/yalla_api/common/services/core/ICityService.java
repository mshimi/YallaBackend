package org.example.yalla_api.common.services.core;

import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.entities.core.Country;
import org.example.yalla_api.common.services.CrudService;
import org.example.yalla_api.common.services.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICityService extends PaginationService<City>, CrudService<City,Long> {


    List<City> findAllCitiesByCountryId(Long countryId);
    Page<City> findAllCitiesByCountryId(Long countryId, Pageable pageable);
}

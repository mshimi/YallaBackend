package org.example.yalla_api.common.services.core;

import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.entities.core.Hotel;
import org.example.yalla_api.common.services.CrudService;
import org.example.yalla_api.common.services.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHotelService extends PaginationService<Hotel>, CrudService<Hotel,Long> {


    List<Hotel> findAllByAreaId(Long areaId);
    Page<Hotel> findAllByAreaId(Long areaId,Pageable pageable);


    List<Hotel> findAllByAreaCityId(Long cityId);
    Page<Hotel> findAllByAreaCityId(Long cityId,Pageable pageable);
}

package org.example.yalla_api.common.services.core;

import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.services.CrudService;
import org.example.yalla_api.common.services.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAreaService extends PaginationService<Area>, CrudService<Area,Long> {


    List<Area> findAllAreaByCityId(Long areaId);

    Page<Area> findAllAreasByCityId(Long areaId, Pageable pageable);
}

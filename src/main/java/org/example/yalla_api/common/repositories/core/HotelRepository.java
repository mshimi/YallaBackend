package org.example.yalla_api.common.repositories.core;

import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.entities.core.Hotel;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends BaseRepository<Hotel,Long> {

   List<Hotel> findAllByAreaId(Long areaId);
   Page<Hotel> findAllByAreaId(Long areaId,Pageable pageable);


   List<Hotel> findAllByAreaCityId(Long areaId);
   Page<Hotel> findAllByAreaCityId(Long areaId,Pageable pageable);

}

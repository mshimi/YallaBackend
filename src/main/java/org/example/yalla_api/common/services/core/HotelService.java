package org.example.yalla_api.common.services.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Metamodel;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.Country;
import org.example.yalla_api.common.entities.core.Hotel;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.repositories.core.AreaRepository;
import org.example.yalla_api.common.repositories.core.HotelRepository;
import org.example.yalla_api.common.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService extends BaseService<Hotel,Long> {


    @Autowired
   private HotelRepository repository;


    public List<Hotel> findAllByAreaId(Long areaId) {
        return repository.findAllByAreaId(areaId);
    }


    public Page<Hotel> findAllByAreaId(Long areaId, Pageable pageable) {
        return repository.findAllByAreaId(areaId,pageable);
    }


    public List<Hotel> findAllByAreaCityId(Long cityId) {
        return repository.findAllByAreaCityId(cityId);
    }


    public Page<Hotel> findAllByAreaCityId(Long cityId, Pageable pageable) {
        return repository.findAllByAreaCityId(cityId,pageable);
    }

    @Override
    protected BaseRepository<Hotel, Long> getRepository() {
        return repository;
    }





}

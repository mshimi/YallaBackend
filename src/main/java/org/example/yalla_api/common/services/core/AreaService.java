package org.example.yalla_api.common.services.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Metamodel;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.core.Country;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.repositories.core.AreaRepository;
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
public class AreaService extends BaseService<Area,Long> {


    @Autowired
   private AreaRepository areaRepository;


    public List<Area> findAllAreaByCityId(Long cityId) {
        return areaRepository.findAllAreaByCityId(cityId);
    }


    public Page<Area> findAllAreasByCityId(Long cityId, Pageable pageable) {
        return areaRepository.findAllAreasByCityId(cityId,pageable);
    }

    @Override
    protected BaseRepository<Area, Long> getRepository() {
        return areaRepository;
    }




}

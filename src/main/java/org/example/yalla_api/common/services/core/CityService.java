package org.example.yalla_api.common.services.core;


import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.repositories.core.CityRepository;
import org.example.yalla_api.common.services.BaseServiceWithImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CityService extends BaseServiceWithImage<City> {

    @Autowired
    private CityRepository cityRepository;

    @Override
    protected BaseRepository<City, Long> getRepository() {
        return cityRepository;
    }


    public List<City> findAllCitiesByCountryId(Long countryId) {
        return cityRepository.findAllCitiesByCountryId(countryId);
    }


    public Page<City> findAllCitiesByCountryId(Long countryId, Pageable pageable) {
        return cityRepository.findAllCitiesByCountryId(countryId, pageable);
    }


    @Override
    protected Image getImage(City entity) {
        return entity.getImage();
    }

    @Override
    protected void setImage(City entity, Image image) {
        entity.setImage(image);
    }
}

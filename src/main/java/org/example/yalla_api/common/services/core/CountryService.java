package org.example.yalla_api.common.services.core;

import org.example.yalla_api.common.entities.core.Country;

import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.repositories.core.CountryRepository;

import org.example.yalla_api.common.services.BaseServiceWithImage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class CountryService extends BaseServiceWithImage<Country> {

    @Autowired
   private CountryRepository countryRepository;




    @Override
    protected BaseRepository<Country, Long> getRepository() {
        return countryRepository;
    }


    @Override
    protected Image getImage(Country entity) {
        return entity.getImage();
    }

    @Override
    protected void setImage(Country entity, Image image) {
        entity.setImage(image);
    }
}

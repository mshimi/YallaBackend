package org.example.yalla_api.common.services.transfer;

import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.repositories.transfer.VehicleRepository;
import org.example.yalla_api.common.services.BaseServiceWithImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService extends BaseServiceWithImage<Vehicle> {

    @Autowired
   private VehicleRepository vehicleRepository;

    @Override
    protected Image getImage(Vehicle entity) {
       return entity.getImage();
    }

    @Override
    protected void setImage(Vehicle entity, Image image) {
        entity.setImage(image);
    }

    @Override
    protected BaseRepository<Vehicle, Long> getRepository() {
        return vehicleRepository;
    }


}

package org.example.yalla_api.common.services.Image;

import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.repositories.core.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image getImageForEntity(Long ownerId, String ownerType) {
        return imageRepository.findByOwnerIdAndOwnerType(ownerId, ownerType);
    }




    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public void delete(Image image) {
        imageRepository.delete(image);
    }

    public void deleteById(Long id) {

        imageRepository.delete(imageRepository.findById(id).orElseThrow());
    }

    public Image getImageById(Long id) {
      return   imageRepository.findById(id).orElseThrow();
    }
}

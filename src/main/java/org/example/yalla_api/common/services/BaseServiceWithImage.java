package org.example.yalla_api.common.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.services.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public abstract class BaseServiceWithImage<T> extends BaseService<T,Long>{


    @Autowired
    private ImageService imageService;

    /**
     * Saves an image and associates it with the entity.
     *

     * @param file  The image to save.
     * @return The updated entity with the associated image.
     */
    @Transactional
    public T saveImage(Long id, MultipartFile file ) throws IOException {
        T entity = getRepository().findById(id).orElseThrow();
        String contentType = file.getContentType();
            if (!contentType.startsWith("image/")) {
                throw new RuntimeException();
            }
        Image image = new Image();


            image.setType(contentType);
            image.setImage(file.getBytes());
            image.setOwnerId(id);
            image.setOwnerType(entity.getClass().getSimpleName());
            Image savedImage = imageService.save(image);


        setImage(entity, savedImage);
        return save(entity);
    }

    /**
     * Deletes the image associated with the entity.
     *
     * @param entityId The ID of the entity whose image should be deleted.
     */
    public void deleteImage(Long entityId) {
        T entity = findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        Image image = getImage(entity);
        if (image != null) {
            imageService.delete(image);
            setImage(entity, null);
            save(entity);
        }
    }

    /**
     * Abstract method to get the image from the entity.
     *
     * @param entity The entity to get the image from.
     * @return The associated image.
     */
    protected abstract Image getImage(T entity);

    /**
     * Abstract method to set the image for the entity.
     *
     * @param entity The entity to associate the image with.
     * @param image  The image to set.
     */
    protected abstract void setImage(T entity, Image image);


}

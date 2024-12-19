package org.example.yalla_api.common.controllers.image;

import org.example.yalla_api.common.entities.core.Country;
//import org.example.yalla_api.common.entities.image.Image;
//import org.example.yalla_api.common.services.Image.ImageService;
import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.services.Image.ImageService;
import org.example.yalla_api.config.annotations.AdminOrControllerOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageByID(@PathVariable Long id){
        try {


            // Get the associated image
            Image image = imageService.getImageById(id);
            if (image == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No image found for the given ID");
            }

            // Return the image as a response
            return ResponseEntity.ok()
                    .header("Content-Type", image.getType()) // Set the content type (e.g., image/png)
                    .header("Content-Disposition", "inline; filename=\"image." + image.getType().split("/")[1] + "\"") // Optional
                    .body(image.getImage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @AdminOrControllerOnly
    public  ResponseEntity<?> deleteImageById(@PathVariable Long id){
        imageService.deleteById(id);
        return ResponseEntity.accepted().body("Image Deleted");
    }


}

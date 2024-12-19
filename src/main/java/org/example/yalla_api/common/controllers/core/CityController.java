package org.example.yalla_api.common.controllers.core;

import jakarta.validation.Valid;
import org.example.yalla_api.common.dtos.core.CityDTO;
import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.mappers.CityMapper;
import org.example.yalla_api.common.services.core.CityService;
import org.example.yalla_api.common.services.core.ICityService;
import org.example.yalla_api.config.annotations.AdminOnly;
import org.example.yalla_api.config.annotations.AdminOrControllerOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/core/city")

public class CityController {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities(@RequestParam Map<String,String> filters ) {
        List<City> cities = new ArrayList<>();

            cities = cityService.findAll(filters);



        return ResponseEntity.ok(cities.stream().map(cityMapper::toDTO).toList());
    }

    @PostMapping
    @AdminOrControllerOnly
    public ResponseEntity<CityDTO> addCity(@Valid @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityMapper.toDTO(cityService.save(cityMapper.toEntity(cityDTO))));
    }

    @PutMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<CityDTO> updateCity( @PathVariable Long id, @Valid @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityMapper.toDTO(cityService.update(id,cityMapper.toEntity(cityDTO))));
    }

    @DeleteMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        cityService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/paginated")
    public Page<CityDTO> getCitiesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String,String> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return cityService.findAll(pageable,filters).map(cityMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityMapper.toDTO(cityService.findById(id).orElseThrow()));
    }

    @PostMapping("/{id}/image")
    @AdminOrControllerOnly
    public ResponseEntity<?> uploadImage(@PathVariable Long id,
                                         @RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(cityMapper.toDTO(cityService.saveImage(id, file)));
    }

    @DeleteMapping("/{id}/image")
    @AdminOrControllerOnly
    public ResponseEntity<?> deleteImage(@PathVariable Long id
    )   {
        cityService.deleteImage(id);
        return ResponseEntity.accepted().body("Image Deleted Successfully");
    }


}
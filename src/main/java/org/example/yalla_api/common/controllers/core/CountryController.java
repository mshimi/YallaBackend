package org.example.yalla_api.common.controllers.core;

import jakarta.validation.Valid;
import org.example.yalla_api.common.dtos.core.CountryDTO;
import org.example.yalla_api.common.mappers.CountryMapper;
import org.example.yalla_api.common.services.core.CountryService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/core/country")
public class CountryController {

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CountryService countryService;

    @GetMapping()
    public ResponseEntity<List<CountryDTO>> getAllCountries(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(countryService.findAll(filters).stream().map(countryMapper::toDTO).toList());
    }


    @PostMapping()
    @AdminOrControllerOnly
    public ResponseEntity<CountryDTO> addCountry(@Valid @RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryMapper.toDTO(countryService.save(countryMapper.toEntity(countryDTO))));
    }

    @PutMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable Long id, @Valid @RequestBody CountryDTO countryDTO) {
        return ResponseEntity.ok(countryMapper.toDTO(countryService.update(id, countryMapper.toEntity(countryDTO))));
    }

    @DeleteMapping("/{id}")
    @AdminOnly
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        countryService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/paginated")
    public Page<CountryDTO> getCountriesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String, String> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);

        System.out.print("Filters: " + filters);

        return countryService.findAll(pageable, filters).map(countryMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable Long id) {

        return ResponseEntity.ok(countryMapper.toDTO(countryService.findById(id).orElseThrow()));
    }


    @PostMapping("/{id}/image")
    @AdminOrControllerOnly
    public ResponseEntity<?> uploadImage(@PathVariable Long id,
                                         @RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(countryMapper.toDTO(countryService.saveImage(id, file)));
    }

    @DeleteMapping("/{id}/image")
    @AdminOrControllerOnly
    public ResponseEntity<?> deleteImage(@PathVariable Long id
                                         )   {
        countryService.deleteImage(id);
        return ResponseEntity.accepted().body("Image Deleted Successfully");
    }

}

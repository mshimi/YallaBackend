package org.example.yalla_api.common.controllers.core;

import jakarta.validation.Valid;
import org.example.yalla_api.common.dtos.core.AirportDTO;
import org.example.yalla_api.common.entities.core.Airport;
import org.example.yalla_api.common.mappers.AirportMapper;
import org.example.yalla_api.common.services.core.AirportService;
import org.example.yalla_api.config.annotations.AdminOnly;
import org.example.yalla_api.config.annotations.AdminOrControllerOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/core/airport")
public class AirportController {

    @Qualifier("airportMapperImpl")
    @Autowired
    private AirportMapper mapper;

    @Autowired
    private AirportService airportService;

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports(@RequestParam Map<String, String> filters) {
        List<Airport> airports = new ArrayList<>();
        airports = airportService.findAll(filters);
        return ResponseEntity.ok(airports.stream().map(mapper::toDTO).toList());
    }

    @PostMapping
    @AdminOrControllerOnly
    public ResponseEntity<AirportDTO> addAirport(@Valid @RequestBody AirportDTO airportDTO) {
        return ResponseEntity.ok(mapper.toDTO(airportService.save(mapper.toEntity(airportDTO))));
    }

    @PutMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<AirportDTO> updateAirport(@PathVariable Long id, @Valid @RequestBody AirportDTO airportDTO) {
        return ResponseEntity.ok(mapper.toDTO(airportService.update(id, mapper.toEntity(airportDTO))));
    }

    @DeleteMapping("/{id}")
    @AdminOnly
    public ResponseEntity<?> deleteAirport(@PathVariable Long id) {
        airportService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AirportDTO>> getAirportsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String, String> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Airport> airportPage = airportService.findAll(pageable, filters);
        Page<AirportDTO> airports = airportPage.map(mapper::toDTO);
        return ResponseEntity.ok(airports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO> getAirportById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(airportService.findById(id).orElseThrow()));
    }
}
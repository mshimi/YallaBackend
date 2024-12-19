package org.example.yalla_api.common.controllers.core;

import jakarta.validation.Valid;
import org.example.yalla_api.common.dtos.core.AreaDTO;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.mappers.AreaMapper;
import org.example.yalla_api.common.services.core.AreaService;
import org.example.yalla_api.common.services.core.IAreaService;
import org.example.yalla_api.config.annotations.AdminOnly;
import org.example.yalla_api.config.annotations.AdminOrControllerOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/core/area")

public class AreaController {

    @Autowired
    private AreaMapper mapper;

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaDTO>> getAllAreas( @RequestParam Map<String,String> filters) {
        List<Area> areas = new ArrayList<>();

            areas = areaService.findAll(filters);

        return ResponseEntity.ok(areas.stream().map(mapper::toDTO).toList());
    }

    @PostMapping
    @AdminOrControllerOnly
    public ResponseEntity<AreaDTO> addArea(@Valid @RequestBody AreaDTO areaDTO) {
       return ResponseEntity.ok(mapper.toDTO(areaService.save(mapper.toEntity(areaDTO))));
    }

    @PutMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<AreaDTO> updateArea(@PathVariable Long id, @Valid @RequestBody AreaDTO areaDTO) {
        return ResponseEntity.ok(mapper.toDTO(areaService.update(id, mapper.toEntity(areaDTO))));
    }

    @DeleteMapping ("/{id}")
    @AdminOnly
    public ResponseEntity<?> deleteArea(@PathVariable Long id) {
        areaService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AreaDTO>> getAreasPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String,String> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Area> areaPage;

            areaPage = areaService.findAll(pageable,filters);

        Page<AreaDTO> areas =  areaPage.map(mapper::toDTO);
        return ResponseEntity.ok(areas);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> getAreaById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(areaService.findById(id).orElseThrow()));
    }
}
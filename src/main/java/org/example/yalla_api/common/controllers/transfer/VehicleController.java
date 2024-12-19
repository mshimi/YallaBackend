package org.example.yalla_api.common.controllers.transfer;

import jakarta.validation.Valid;
import org.example.yalla_api.common.dtos.transfer.VehicleDTO;
import org.example.yalla_api.common.entities.transfer.Vehicle;
import org.example.yalla_api.common.mappers.VehicleMapper;
import org.example.yalla_api.common.services.transfer.VehicleService;
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
@RequestMapping("/transfer/vehicle")
public class VehicleController {

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private VehicleService vehicleService;


    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles(@RequestParam Map<String,String> filters ) {
        List<Vehicle> vehicles = new ArrayList<>();

        vehicles = vehicleService.findAll(filters);



        return ResponseEntity.ok(vehicles.stream().map(vehicleMapper::toDTO).toList());
    }

    @PostMapping
    @AdminOrControllerOnly
    public ResponseEntity<VehicleDTO> addVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleMapper.toDTO(vehicleService.save(vehicleMapper.toEntity(vehicleDTO))));
    }

    @PutMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<VehicleDTO> updateVehicle( @PathVariable Long id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleMapper.toDTO(vehicleService.update(id,vehicleMapper.toEntity(vehicleDTO))));
    }

    @DeleteMapping
    @AdminOnly
    public ResponseEntity<?> deleteVehicle(@RequestParam Long id) {
        vehicleService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/paginated")
    public Page<VehicleDTO> getVehiclesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String,String> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return vehicleService.findAll(pageable,filters).map(vehicleMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleMapper.toDTO(vehicleService.findById(id).orElseThrow()));
    }

    @PostMapping("/{id}/image")
    @AdminOrControllerOnly
    public ResponseEntity<?> uploadImage(@PathVariable Long id,
                                         @RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(vehicleMapper.toDTO(vehicleService.saveImage(id, file)));
    }

    @DeleteMapping("/{id}/image")
    @AdminOrControllerOnly
    public ResponseEntity<?> deleteImage(@PathVariable Long id
    )   {
        vehicleService.deleteImage(id);
        return ResponseEntity.accepted().body("Image Deleted Successfully");
    }



}

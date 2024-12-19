package org.example.yalla_api.common.controllers.core;

import jakarta.validation.Valid;
import org.example.yalla_api.common.dtos.core.HotelDTO;
import org.example.yalla_api.common.entities.core.Hotel;
import org.example.yalla_api.common.mappers.HotelMapper;
import org.example.yalla_api.common.services.core.HotelService;
import org.example.yalla_api.common.services.core.IHotelService;
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
@RequestMapping("/core/hotel")
public class HotelController {

    @Autowired
    private HotelMapper mapper;

    @Autowired
    private HotelService hotelService;


    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels(@RequestParam Map<String,String> filters) {
        List<Hotel> hotels = new ArrayList<>();

            hotels = hotelService.findAll(filters);

        return ResponseEntity.ok(hotels.stream().map(mapper::toDTO).toList());
    }


    @PostMapping
    @AdminOrControllerOnly
    public ResponseEntity<HotelDTO> addHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        System.out.println(hotelDTO);
        try {
            Hotel hotel = mapper.toEntity(hotelDTO);
            System.out.println("This is Hotel Entity: " + hotel);
            HotelDTO hotelDTO1 = mapper.toDTO(hotelService.save(hotel));
            System.out.println(hotelDTO);
            return ResponseEntity.ok(hotelDTO1);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }


    }

    @PutMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id,@Valid @RequestBody HotelDTO dto) {
        return ResponseEntity.ok(mapper.toDTO(hotelService.update(id,mapper.toEntity(dto))));
    }


    @DeleteMapping("/{id}")
    @AdminOrControllerOnly
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<HotelDTO>> getHotelsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(required = false) Map<String,String> filters
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotels;


            hotels = hotelService.findAll(pageable,filters);

        return ResponseEntity.ok(hotels.map(mapper::toDTO));


    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(hotelService.findById(id).orElseThrow()));
    }

}

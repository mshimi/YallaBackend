package org.example.yalla_api.admin.controllers.transfer;


import jakarta.validation.Valid;
import org.example.yalla_api.admin.dtos.transfer.TestBookingRequestDTO;
import org.example.yalla_api.admin.dtos.transfer.TestBookingResponse;
import org.example.yalla_api.admin.dtos.transfer.TestBookingResponseDTO;
import org.example.yalla_api.admin.dtos.transfer.TransferExtraDTO;
import org.example.yalla_api.admin.mappers.transfer.TestBookingResponseMapper;
import org.example.yalla_api.admin.services.TransferTestBookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/transfer/testbooking")
public class TestBookingController {


    @Autowired
    private TransferTestBookingService transferTestBookingService;


    @Autowired
  private  TestBookingResponseMapper testBookingResponseMapper;

    @PostMapping
    public ResponseEntity<TestBookingResponseDTO> testBooking(@RequestBody @Valid TestBookingRequestDTO dto) {
      TestBookingResponse response = transferTestBookingService.testBooking(dto.getStartAreaId(), dto.getEndAreaId(), dto.getAdults(),dto.getChild(),dto.getStartDate(),dto.getExtras());

        return ResponseEntity.ok(testBookingResponseMapper.toDto(response));
    }

}

package org.example.yalla_api.admin.controllers.transfer;

import jakarta.validation.Valid;
import org.example.yalla_api.admin.dtos.transfer.AddTransferRateDTO;
import org.example.yalla_api.admin.dtos.transfer.TransferRateDTO;
import org.example.yalla_api.admin.services.TransferRateService;
import org.example.yalla_api.common.entities.auditmetadata.AuditEntityListener;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.mappers.TransferRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/transfer/rates")
public class TransferRateController {


    private static final Logger logger = LoggerFactory.getLogger(TransferRateController.class);



    @Autowired
    private TransferRateService transferRateService;

    @Autowired
    private TransferRateMapper transferRateMapper;

    /**
     * Get all active TransferRates.
     *
     * @return List of active TransferRates.
     */
    @GetMapping()
    public ResponseEntity<Page<TransferRateDTO>> getActiveTransferRates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Map<String, String> filters

    ) {
        filters.forEach((k,v)-> {
                logger.error(k + " : " + v);
        });
        Pageable pageable = PageRequest.of(page,size);
        Page<TransferRate> activeRates = transferRateService.getActiveTransferRates(pageable,filters);
        return ResponseEntity.ok(activeRates.map(transferRateMapper::toDto));
    }
    /**
     * Add a new TransferRate using area IDs.
     *
     *
     * @return The created TransferRate.
     */
    @PostMapping
    public ResponseEntity<TransferRateDTO> addTransferRate(
            @Valid  @RequestBody AddTransferRateDTO dto) {
        TransferRate transferRate = transferRateService.addTransferRate(dto.getSourceAreaId(), dto.getDestinationAreaId(), dto.getRate(), dto.getRelease());
        return ResponseEntity.ok(transferRateMapper.toDto(transferRate));
    }

    @PostMapping("/all")
    public ResponseEntity<List<TransferRateDTO>> addTransferRatesAll(
            @Valid  @RequestBody List<AddTransferRateDTO> dto) {
        List<TransferRate> rates = transferRateService.addTransferRatesFromDTO(dto);
        return ResponseEntity.ok(rates.stream().map(transferRateMapper::toDto).toList());
    }

    /**
     * Add multiple TransferRates.
     *
     * @param rates List of TransferRateDTO objects.
     * @return List of created TransferRates.
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<TransferRateDTO>> addTransferRates(@RequestBody List<TransferRateDTO> rates) {
        List<TransferRate> transferRates = transferRateService.addTransferRates(rates.stream()
                .map(transferRateMapper::toEntity).toList());
        return ResponseEntity.ok(transferRates.stream().map(transferRateMapper::toDto).toList());
    }

    /**
     * Get a specific TransferRate for given source and destination areas.
     *
     * @param sourceAreaId      Source Area ID.
     * @param destinationAreaId Destination Area ID.
     * @return The TransferRate for the given areas.
     */
    @GetMapping("/calc")
    public ResponseEntity<TransferRateDTO> getTransferRateForAreas(
            @RequestParam Long sourceAreaId,
            @RequestParam Long destinationAreaId
    ) {
        TransferRate transferRate = transferRateService.getTransferRateForAreas(sourceAreaId, destinationAreaId);
        return ResponseEntity.ok(transferRateMapper.toDto(transferRate));
    }

    /**
     * Deactivate a specific TransferRate.
     *
     * @param id TransferRate ID.
     * @return ResponseEntity with no content.
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> setTransferRateInactive(@PathVariable Long id) {
        transferRateService.setTransferRateInactive(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<TransferRateDTO> setTransferRateActive(@PathVariable Long id) {

        return ResponseEntity.ok().body(transferRateMapper.toDto(transferRateService.reactivateTransferRate(id)));
    }

}

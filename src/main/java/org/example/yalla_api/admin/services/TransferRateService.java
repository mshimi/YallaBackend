package org.example.yalla_api.admin.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.yalla_api.admin.dtos.transfer.AddTransferRateDTO;
import org.example.yalla_api.common.entities.core.Area;
import org.example.yalla_api.common.entities.transfer.TransferRate;
import org.example.yalla_api.common.repositories.core.AreaRepository;
import org.example.yalla_api.common.repositories.transfer.TransferRateRepository;
import org.example.yalla_api.common.utils.FilterSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransferRateService  {

    @Autowired
    private TransferRateRepository transferRateRepository;


    @Autowired
    AreaRepository areaRepository;

    /**
     * Add a new TransferRate using area IDs.
     */

    public TransferRate addTransferRate(Long sourceAreaId, Long destinationAreaId, Double rate, Integer release) {
        // Ensure no active rate exists for the same source and destination areas
        deactivateExistingRates(sourceAreaId, destinationAreaId);

        Area sourceArea = areaRepository.findById(sourceAreaId).orElseThrow(() -> new EntityNotFoundException("Source Area not Found"));
        Area destinationArea = areaRepository.findById(destinationAreaId).orElseThrow(() -> new EntityNotFoundException("Destination Area not Found"));

        TransferRate transferRate = new TransferRate();
        transferRate.setSourceArea(sourceArea);
        transferRate.setDestinationArea(destinationArea);
        transferRate.setRatePerPerson(rate);
        transferRate.setIsActive(true);

        if(release != null){
            transferRate.setRelease(release);
        }

        return transferRateRepository.save(transferRate);
    }



    public List<TransferRate> addTransferRatesFromDTO(List<AddTransferRateDTO> rateDtos) {
        // Process each DTO and add transfer rates
        return rateDtos.stream().map(dto -> {
            deactivateExistingRates(dto.getSourceAreaId(), dto.getDestinationAreaId());

            Area sourceArea = areaRepository.findById(dto.getSourceAreaId())
                    .orElseThrow(() -> new EntityNotFoundException("Source Area not Found"));
            Area destinationArea = areaRepository.findById(dto.getDestinationAreaId())
                    .orElseThrow(() -> new EntityNotFoundException("Destination Area not Found"));

            TransferRate transferRate = new TransferRate();
            transferRate.setSourceArea(sourceArea);
            transferRate.setDestinationArea(destinationArea);
            transferRate.setRatePerPerson(dto.getRate());
            transferRate.setRelease(dto.getRelease());
            transferRate.setIsActive(true);

            return transferRateRepository.save(transferRate);
        }).toList();
    }

    /**
     * Add a new TransferRate using Area objects.
     */
    public TransferRate addTransferRate(Area sourceArea, Area destinationArea, Double rate) {
        // Ensure no active rate exists for the same source and destination areas
        deactivateExistingRates(sourceArea.getId(), destinationArea.getId());

        TransferRate transferRate = new TransferRate();
        transferRate.setSourceArea(sourceArea);
        transferRate.setDestinationArea(destinationArea);
        transferRate.setRatePerPerson(rate);
        transferRate.setIsActive(true);

        return transferRateRepository.save(transferRate);
    }
    private final FilterSpecificationBuilder<TransferRate> filterSpecificationBuilder = new FilterSpecificationBuilder<>();


    /**
     * Get all active TransferRates.
     */
    public Page<TransferRate> getActiveTransferRates(Pageable pageable, Map<String, String> filters) {

        if(filters != null && !filters.isEmpty() ){

            Specification<TransferRate> specification = filterSpecificationBuilder.buildSpecification(filters);

            return transferRateRepository.findAll(specification,pageable);
        }
        return transferRateRepository.findAllByIsActiveIsTrue(pageable);
    }

    /**
     * Get the TransferRate for specific source and destination area IDs.
     */
    public TransferRate getTransferRateForAreas(Long sourceAreaId, Long destinationAreaId) {
        return transferRateRepository.findActiveTransferRateForAreas(sourceAreaId, destinationAreaId)
                .orElseThrow(() -> new IllegalArgumentException("No active TransferRate found for the given areas."));
    }

    /**
     * Get the TransferRate for specific source and destination Area objects.
     */
    public TransferRate getTransferRateForAreas(Area sourceArea, Area destinationArea) {
        return transferRateRepository.findBySourceAreaAndDestinationAreaAndIsActiveTrue(sourceArea, destinationArea)
                .orElseThrow(() -> new IllegalArgumentException("No active TransferRate found for the given areas."));
    }

    /**
     * Add multiple TransferRates and deactivate conflicting active rates.
     */
    public List<TransferRate> addTransferRates(List<TransferRate> rates) {
        rates.forEach(rate -> deactivateExistingRates(
                rate.getSourceArea().getId(),
                rate.getDestinationArea().getId()
        ));
        return transferRateRepository.saveAll(rates);
    }

    /**
     * Set a specific TransferRate as inactive.
     */
    public void setTransferRateInactive(Long id) {
        TransferRate transferRate = transferRateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TransferRate not found with ID: " + id));

        transferRate.setIsActive(false);
        transferRateRepository.save(transferRate);
    }



    /**
     * reactive transferRate
     */
    @Transactional
    public TransferRate reactivateTransferRate(Long id) {
        TransferRate transferRate = transferRateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TransferRate not found with ID: " + id));

        if(transferRate.getIsActive()){
            throw new IllegalArgumentException("TransferRate is already active");
        }

        deactivateExistingRates(transferRate.getSourceArea().getId(),transferRate.getDestinationArea().getId());

        transferRate.setIsActive(true);


      return  transferRateRepository.save(transferRate);
    }


    /**
     * Helper to deactivate existing active rates for the given source and destination areas.
     */
    private void deactivateExistingRates(Long sourceAreaId, Long destinationAreaId) {
        List<TransferRate> existingRates = transferRateRepository.findBySourceAreaIdAndDestinationAreaIdAndIsActiveTrue(sourceAreaId, destinationAreaId);

        existingRates.forEach(rate -> rate.setIsActive(false));
        transferRateRepository.saveAll(existingRates);
    }
}
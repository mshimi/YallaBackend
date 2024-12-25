package org.example.yalla_api.admin.controllers.transfer;


import org.example.yalla_api.admin.dtos.transfer.BackToContractDTO;
import org.example.yalla_api.admin.dtos.transfer.ReleasePeriodDTO;
import org.example.yalla_api.admin.mappers.transfer.TransferReleaseMapper;
import org.example.yalla_api.common.entities.Release.ReleasePeriod;
import org.example.yalla_api.common.services.transfer.TransferReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/release")
public class ReleaseController {

    @Autowired
    TransferReleaseService releaseService;

    @Autowired
    @Qualifier("transferReleaseMapperImpl")
    TransferReleaseMapper mapper;


    @GetMapping("/transfer")
    public ResponseEntity<?> getActiveReleasePeriod() {
        return ResponseEntity.ok(releaseService.getActiveReleasePeriod().stream().map(mapper::toDTO));
    }

    @PostMapping("/transfer/general")
    public ResponseEntity<?> addGeneralReleasePeriod(@RequestBody ReleasePeriodDTO releasePeriodDTO) {
        return ResponseEntity.ok(mapper.toDTO(releaseService.addGeneralRelease(mapper.toEntity(releasePeriodDTO))));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> addReleasePeriod(@RequestBody ReleasePeriodDTO releasePeriodDTO) {
        return ResponseEntity.ok(mapper.toDTO(releaseService.addReleasePeriod(mapper.toEntity(releasePeriodDTO))));
    }

    @PostMapping("/transfer/backtogeneral")
    public ResponseEntity<?> backToGeneralRelease(@RequestBody BackToContractDTO backToContractDTO) {
        return ResponseEntity.ok(
                releaseService.backToGeneralRelease(backToContractDTO.getStartDate(), backToContractDTO.getEndDate())
                        .stream().map(mapper::toDTO));
    }


    @DeleteMapping("/transfer/{id}")
    public ResponseEntity<?> setReleaseAsInActive(@PathVariable Long id) {
        releaseService.setReleaseAsInActive(id);
        return ResponseEntity.accepted().body("Release Deleted");
    }


    @GetMapping("/transfer/general")
    public ResponseEntity<?> getGeneralRelease() {
        ReleasePeriod release = releaseService.getGeneralReleasePeriod();

        if (release != null) {
            return ResponseEntity.ok(mapper.toDTO(release));
        }
        return ResponseEntity.ok(null);

    }


}

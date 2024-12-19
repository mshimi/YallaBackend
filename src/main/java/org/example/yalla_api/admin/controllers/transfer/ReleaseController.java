package org.example.yalla_api.admin.controllers.transfer;


import org.example.yalla_api.admin.mappers.transfer.TransferReleaseMapper;
import org.example.yalla_api.common.entities.Release.ReleasePeriod;
import org.example.yalla_api.common.services.transfer.TransferReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/release")
public class ReleaseController {

    @Autowired
    TransferReleaseService releaseService;

    @Autowired
    TransferReleaseMapper mapper;


    @GetMapping("/transfer")
    public ResponseEntity<?> getActiveReleasePeriod() {
        return ResponseEntity.ok(releaseService.getActiveReleasePeriod().stream().map(mapper::toDTO));
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

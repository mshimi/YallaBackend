package org.example.yalla_api.admin.controllers.transfer;

import org.example.yalla_api.admin.dtos.transfer.TransferExtraDTO;
import org.example.yalla_api.admin.mappers.transfer.TransferExtraMapper;
import org.example.yalla_api.common.entities.transfer.TransferExtra;
import org.example.yalla_api.common.entities.transfer.TransferExtraTranslation;
import org.example.yalla_api.common.enums.Language;
import org.example.yalla_api.common.services.transfer.TransferExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/transfer/extra")
public class TransferExtraController {

    @Autowired
    private TransferExtraService transferExtraService;

    @Autowired
    private TransferExtraMapper transferExtraMapper;

    /**
     * Get all TransferExtras.
     */
    @GetMapping
    public ResponseEntity<List<TransferExtraDTO>> getAllTransferExtras() {
        List<TransferExtra> transferExtras = transferExtraService.findAllTransferExtras();
        return ResponseEntity.ok(transferExtras.stream()
                .map(transferExtraMapper::toDTO)
                .collect(Collectors.toList()));
    }

    /**
     * Get a TransferExtra by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransferExtraDTO> getTransferExtraById(@PathVariable Long id) {
        TransferExtra transferExtra = transferExtraService.getTransferExtraById(id);
        return ResponseEntity.ok(transferExtraMapper.toDTO(transferExtra));
    }

    /**
     * Get all translations for a TransferExtra.
     */
    @GetMapping("/{id}/translations")
    public ResponseEntity<List<TransferExtraTranslation>> getTranslationsByTransferExtraId(@PathVariable Long id) {
        return ResponseEntity.ok(transferExtraService.getTranslationsByTransferExtraId(id));
    }

    /**
     * Save a new TransferExtra.
     */
    @PostMapping
    public ResponseEntity<TransferExtraDTO> saveTransferExtra(@RequestBody TransferExtraDTO transferExtraDTO) {
        TransferExtra transferExtra = transferExtraMapper.toEntity(transferExtraDTO);
        TransferExtra savedTransferExtra = transferExtraService.saveTransferExtra(transferExtra);
        return ResponseEntity.ok(transferExtraMapper.toDTO(savedTransferExtra));
    }

    /**
     * Update the pax value of a TransferExtra.
     */
    @PatchMapping("/{id}/pax-value")
    public ResponseEntity<Void> updatePaxValue(@PathVariable Long id, @RequestParam Double paxValue) {
        transferExtraService.updatePaxValue(id, paxValue);
        return ResponseEntity.accepted().build();
    }

    /**
     * Set a TransferExtra as not valid.
     */
    @PatchMapping("/{id}/invalidate")
    public ResponseEntity<Void> setAsNotValid(@PathVariable Long id) {
        transferExtraService.setAsNotValid(id);
        return ResponseEntity.accepted().build();
    }

    /**
     * Update a translation of a TransferExtra.
     */
//    @PatchMapping("/{id}/translation")
//    public ResponseEntity<Void> updateTranslation(@PathVariable Long id,
//                                                  @RequestParam Language lang,
//                                                  @RequestParam String name) {
//        transferExtraService.updateTranslation(id, lang, name);
//        return ResponseEntity.accepted().build();
//    }

    /**
     * Delete a translation of a TransferExtra.
     */
    @DeleteMapping("/translation/{id}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long id) {
        transferExtraService.deleteTranslation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a new translation to a TransferExtra.
     */
    @PatchMapping("/{id}/translation")
    public ResponseEntity<Void> addTranslation(@PathVariable Long id,
                                               @RequestParam Language lang,
                                               @RequestParam String name) {
        transferExtraService.addTranslation(id, lang, name);
        return ResponseEntity.accepted().build();
    }

    /**
     * Upload an image for a TransferExtra.
     */
    @PostMapping("/{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        transferExtraService.uploadImage(id, file);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete the image of a TransferExtra.
     */
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        transferExtraService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}

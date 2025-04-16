package org.example.yalla_api.common.services.transfer;

import jakarta.transaction.Transactional;
import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.entities.transfer.TransferExtra;
import org.example.yalla_api.common.entities.transfer.TransferExtraTranslation;
import org.example.yalla_api.common.enums.Language;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.repositories.transfer.TransferExtraRepository;
import org.example.yalla_api.common.repositories.transfer.TransferExtraTranslationRepository;
import org.example.yalla_api.common.services.BaseService;
import org.example.yalla_api.common.services.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class TransferExtraService extends BaseService<TransferExtra,Long> {

    @Autowired
    private TransferExtraRepository transferExtraRepository;

    @Autowired
    private TransferExtraTranslationRepository transferExtraTranslationRepository;

    @Autowired
    private ImageService imageService;


    /**
     * Get a TransferExtra by its ID.
     *
     * @param
     * @return The found TransferExtra.
     */

    public List<TransferExtra> findAllTransferExtras() {
        return transferExtraRepository.findAllByIsValidIsTrue();
    }

    /**
     * Get a TransferExtra by its ID.
     *
     * @param id The ID of the TransferExtra.
     * @return The found TransferExtra.
     */
    public TransferExtra getTransferExtraById(Long id) {
        return transferExtraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TransferExtra not found with ID: " + id));
    }

    /**
     * Get all translations for a TransferExtra.
     *
     * @param transferExtraId The ID of the TransferExtra.
     * @return A list of translations.
     */
    public List<TransferExtraTranslation> getTranslationsByTransferExtraId(Long transferExtraId) {
        TransferExtra transferExtra = transferExtraRepository.findById(transferExtraId)
                .orElseThrow(() -> new IllegalArgumentException("TransferExtra not found with ID: " + transferExtraId));
        return transferExtra.getTranslations();
    }

    /**
     * Save a new TransferExtra, including at least one translation.
     *
     * @param transferExtra The TransferExtra to save.
     * @return The saved TransferExtra.
     */
    @Transactional
    public TransferExtra saveTransferExtra(TransferExtra transferExtra) {
        if (transferExtra.getTranslations() == null || transferExtra.getTranslations().isEmpty()) {
            throw new IllegalArgumentException("TransferExtra must have at least one translation.");
        }

        // Ensure that each translation's parent is set correctly
        transferExtra.getTranslations().forEach(translation -> translation.setTransferExtra(transferExtra));

        return transferExtraRepository.save(transferExtra);
    }

    /**
     * Update the pax value of a TransferExtra.
     *
     * @param id       The ID of the TransferExtra.
     * @param paxValue The new pax value.
     */
    @Transactional
    public void updatePaxValue(Long id, Double paxValue) {
        TransferExtra transferExtra = transferExtraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TransferExtra not found with ID: " + id));
        transferExtra.setPaxValue(paxValue);
        transferExtraRepository.save(transferExtra);
    }

    /**
     * Set a TransferExtra as not valid (mark as inactive).
     *
     * @param id The ID of the TransferExtra.
     */
    @Transactional
    public void setAsNotValid(Long id) {
        TransferExtra transferExtra = transferExtraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TransferExtra not found with ID: " + id));
        transferExtra.setIsValid(false);
        transferExtraRepository.save(transferExtra);
    }

    /**
     * Update a translation of a TransferExtra.
     *
     * @param transferExtraId The ID of the TransferExtra.
     * @param lang            The language of the translation to update.
     * @param name            The new name for the translation.
     */
    @Transactional
    public void updateTranslation(Long transferExtraId, Language lang, String name) {
        TransferExtraTranslation translation = transferExtraTranslationRepository.findByTransferExtra_IdAndLang(transferExtraId, lang)
                .orElseThrow(() -> new IllegalArgumentException("Translation not found for TransferExtra ID: " + transferExtraId + " and language: " + lang));
        translation.setName(name);
        transferExtraTranslationRepository.save(translation);
    }

    /**
     * Delete a translation of a TransferExtra.
     *
     * @param translationId The ID of the translation to delete.
     */
    @Transactional
    public void deleteTranslation(Long translationId) {
        var translation = transferExtraTranslationRepository.findById(translationId).orElseThrow();

        if (transferExtraTranslationRepository.countAllByTransferExtra(translation.getTransferExtra()) <=1  ) {
            throw new IllegalArgumentException("cant delete all translation of one Extra");
        } else {
            transferExtraTranslationRepository.delete(translation);
        }

    }

    /**
     * Add a new translation to a TransferExtra.
     *
     * @param transferExtraId The ID of the TransferExtra.
     * @param lang            The language of the new translation.
     * @param name            The name for the new translation.
     */
    @Transactional
    public void addTranslation(Long transferExtraId, Language lang, String name) {
        TransferExtra transferExtra = transferExtraRepository.findById(transferExtraId)
                .orElseThrow(() -> new IllegalArgumentException("TransferExtra not found with ID: " + transferExtraId));

        Optional<TransferExtraTranslation> existingTranslation = transferExtraTranslationRepository.findByTransferExtra_IdAndLang(transferExtra.getId(), lang);

        if (existingTranslation.isEmpty()) {
            TransferExtraTranslation newTranslation = new TransferExtraTranslation();
            newTranslation.setTransferExtra(transferExtra);
            newTranslation.setLang(lang);
            newTranslation.setName(name);
            transferExtraTranslationRepository.save(newTranslation);
        } else {
            var trans = existingTranslation.get();
            trans.setName(name);
            transferExtraTranslationRepository.save(trans);
        }


    }

    /**
     * Upload an image for a TransferExtra.
     *
     * @param transferExtraId The ID of the TransferExtra.
     * @param file            The image file to upload.
     * @throws IOException If an error occurs while reading the file.
     */
    @Transactional
    public void uploadImage(Long transferExtraId, MultipartFile file) throws IOException {
        TransferExtra transferExtra = transferExtraRepository.findById(transferExtraId)
                .orElseThrow(() -> new IllegalArgumentException("TransferExtra not found with ID: " + transferExtraId));

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only images are allowed.");
        }

        Image image = new Image();
        image.setType(contentType);
        image.setImage(file.getBytes());
        image.setOwnerId(transferExtraId);
        image.setOwnerType(transferExtra.getClass().getSimpleName());

        Image savedImage = imageService.save(image);
        transferExtra.setImage(savedImage);
        transferExtraRepository.save(transferExtra);
    }

    /**
     * Delete the image of a TransferExtra.
     *
     * @param transferExtraId The ID of the TransferExtra.
     */
    @Transactional
    public void deleteImage(Long transferExtraId) {
        TransferExtra transferExtra = transferExtraRepository.findById(transferExtraId)
                .orElseThrow(() -> new IllegalArgumentException("TransferExtra not found with ID: " + transferExtraId));

        if (transferExtra.getImage() != null) {
            imageService.delete(transferExtra.getImage());
            transferExtra.setImage(null);
            transferExtraRepository.save(transferExtra);
        }
    }


    @Override
    protected BaseRepository<TransferExtra, Long> getRepository() {
        return this.transferExtraRepository;
    }
}

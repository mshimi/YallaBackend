package org.example.yalla_api.common.repositories.transfer;

import org.example.yalla_api.common.entities.transfer.TransferExtraTranslation;
import org.example.yalla_api.common.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferExtraTranslationRepository extends JpaRepository<TransferExtraTranslation, Long> {


    Optional<TransferExtraTranslation> findByTransferExtra_IdAndLang(Long transferExtraId, Language lang);
}

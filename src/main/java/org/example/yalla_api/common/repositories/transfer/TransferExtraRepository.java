package org.example.yalla_api.common.repositories.transfer;

import org.example.yalla_api.common.entities.transfer.TransferExtra;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferExtraRepository extends BaseRepository<TransferExtra, Long> {



    List<TransferExtra> findAllByIsValidIsTrue();


}

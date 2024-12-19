package org.example.yalla_api.common.repositories.transfer;

import org.example.yalla_api.common.entities.transfer.TransferResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferResponseRepository extends JpaRepository<TransferResponse, Long> {
}
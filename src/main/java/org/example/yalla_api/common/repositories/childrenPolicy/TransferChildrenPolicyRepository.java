package org.example.yalla_api.common.repositories.childrenPolicy;

import org.example.yalla_api.common.entities.childrenPolicy.ChildrenPolicy;
import org.example.yalla_api.common.entities.childrenPolicy.TransferChildrenPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferChildrenPolicyRepository extends JpaRepository<TransferChildrenPolicy,Long> {


    @Query("SELECT c FROM ChildrenPolicy c WHERE c.isActive = true")
    Optional<TransferChildrenPolicy> findActivePolicy();

    @Query("SELECT c FROM ChildrenPolicy c WHERE c.isActive = true")
    List<TransferChildrenPolicy> findAllActivePolicies();

}

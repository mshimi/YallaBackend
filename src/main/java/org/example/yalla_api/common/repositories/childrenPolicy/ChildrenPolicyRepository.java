package org.example.yalla_api.common.repositories.childrenPolicy;

import org.example.yalla_api.common.entities.childrenPolicy.ChildrenPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChildrenPolicyRepository extends JpaRepository<ChildrenPolicy, Long> {

    /**
     * Finds the active ChildrenPolicy.
     *
     * @return The active ChildrenPolicy, if found.
     */
    @Query("SELECT c FROM ChildrenPolicy c WHERE c.isActive = true")
    Optional<ChildrenPolicy> findActivePolicy();
}
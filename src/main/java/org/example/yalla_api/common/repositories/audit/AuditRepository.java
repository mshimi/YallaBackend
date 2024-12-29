package org.example.yalla_api.common.repositories.audit;

import org.example.yalla_api.common.entities.auditmetadata.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Long> {
}

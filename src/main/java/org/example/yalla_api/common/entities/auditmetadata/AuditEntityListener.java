package org.example.yalla_api.common.entities.auditmetadata;

import jakarta.persistence.*;
import org.example.yalla_api.common.repositories.audit.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component()
public class AuditEntityListener {

    private static final Logger logger = LoggerFactory.getLogger(AuditEntityListener.class);


    @Autowired
    @Lazy
    private AuditRepository auditRepository;


    @Autowired
    @Lazy
    private EntityManager entityManager;

    private Long getEntityId(Object entity) {
        try {
            // Use reflection to get the ID. Adapt this if your ID field is named differently or has a different type.
            java.lang.reflect.Method getIdMethod = entity.getClass().getMethod("getId");
            Object idValue = getIdMethod.invoke(entity);

            if (idValue instanceof Number) {
                return ((Number) idValue).longValue();
            } else if (idValue != null) {
                return Long.parseLong(idValue.toString());
            }

        } catch (ReflectiveOperationException e) {
            // Handle exceptions appropriately (log, throw custom exception, etc.)
            System.err.println("Error getting entity ID: " + e.getMessage());
            return null; // Or throw an exception
        }
        return null; // Return null if ID cannot be extracted
    }

    private String determineAction(Object entity) {
        // More robust approach using EntityManager (if available) would be better.
        // This is a simplified approach.
        if (entity == null) {
            return "UNKNOWN";
        }
        if (jakarta.persistence.Persistence.getPersistenceUtil().isLoaded(entity)) {
            return "UPDATE";
        } else  {
            return "CREATE"; // Assuming it's a new entity if not loaded.
        }
    }


    private String getCurrentUser() {
        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails)principal).getUsername();
            } else {
                return principal.toString();
            }
        } catch (NullPointerException e) {
            return "system";
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void auditChange(Object entity ) {
        logger.info("Audit Change Triggered"); // Check if this is logged

        String action = determineAction(entity);
        if (action.equals("UNKNOWN")) {
            return;
        }

        Audit audit = new Audit();
        audit.setEntity(entity.getClass().getSimpleName());
        logger.info("Audit Entity: {}", audit); // Check audit data

        audit.setEntityId(getEntityId(entity));
        audit.setAction(action);
        audit.setUser(getCurrentUser());
        audit.setTimestamp(LocalDateTime.now());
        auditRepository.save(audit);
        logger.info("Audit Saved");
    }
}
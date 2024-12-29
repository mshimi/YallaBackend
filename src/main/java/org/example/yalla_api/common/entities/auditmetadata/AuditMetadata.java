//package org.example.yalla_api.common.entities.auditmetadata;
//
//import jakarta.persistence.Embeddable;
//import jakarta.persistence.EntityListeners;
//import org.example.yalla_api.common.entities.auth.User;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.Instant;
//@Embeddable
//public class AuditMetadata {
//
//    @CreatedBy
//    private Long createdById; // Store User ID instead of User entity
//
//    @LastModifiedBy
//    private Long lastModifiedById; // Store User ID instead of User entity
//
//    @CreatedDate
//    private Instant createdDate;
//
//    @LastModifiedDate
//    private Instant lastModifiedDate;
//
//    // Getters and Setters
//    public Long getCreatedById() {
//        return createdById;
//    }
//
//    public void setCreatedById(Long createdById) {
//        this.createdById = createdById;
//    }
//
//    public Long getLastModifiedById() {
//        return lastModifiedById;
//    }
//
//    public void setLastModifiedById(Long lastModifiedById) {
//        this.lastModifiedById = lastModifiedById;
//    }
//
//    public Instant getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Instant createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Instant getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public void setLastModifiedDate(Instant lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }
//}
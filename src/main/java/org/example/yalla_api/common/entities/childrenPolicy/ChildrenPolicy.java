package org.example.yalla_api.common.entities.childrenPolicy;


import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.auth.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "policy_type", discriminatorType = DiscriminatorType.STRING)
@Data
@EntityListeners(AuditingEntityListener.class)
abstract public class ChildrenPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "childrenPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgeRange> ageRanges;

    private boolean isActive;


}


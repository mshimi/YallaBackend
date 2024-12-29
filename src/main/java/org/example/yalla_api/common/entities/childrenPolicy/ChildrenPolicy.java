package org.example.yalla_api.common.entities.childrenPolicy;


import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.auth.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
abstract public class ChildrenPolicy {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Use AUTO for TABLE_PER_CLASS
    private Long id;


    @OneToMany(mappedBy = "childrenPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgeRange> ageRanges;

    private boolean isActive = true;


}


package org.example.yalla_api.common.entities.core;


import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.entities.auditmetadata.AuditEntityListener;
import org.springframework.data.annotation.Reference;

@Entity
@Data
@Table(
        name = "area",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"areaName", "city_id"})
        })
@EntityListeners(AuditEntityListener.class) // Register the listener
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String areaName;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "city_id")
    private City city;
}

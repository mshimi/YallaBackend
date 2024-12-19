package org.example.yalla_api.common.entities.image;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Objects;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type = "image/webp";

    @Column(name = "image", unique = false, nullable = false)
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] image;


    @Override
    public int hashCode() {
        return Objects.hash(id, image, type);
    }

    @Column(name = "owner_id", nullable = false)
    private Long ownerId; // References the owning entity's ID

    @Column(name = "owner_type", nullable = false)
    private String ownerType; // Specifies the type of owning entity (e.g., "Country", "City", etc.)

}

package org.example.yalla_api.common.entities.auth;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.yalla_api.common.enums.Role;

@Entity
@DiscriminatorValue("USER")
@Data
@EqualsAndHashCode(callSuper = true)
public class RegularUser extends User {

    // User-specific fields (if any)

    public RegularUser() {
        this.setRole(Role.USER);
    }
}

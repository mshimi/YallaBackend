package org.example.yalla_api.common.entities.auth;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.yalla_api.common.enums.Role;

@Entity
@DiscriminatorValue("CONTROLLER")
@Data
@EqualsAndHashCode(callSuper = true)
public class ControllerUser extends User {

    // Controller-specific fields (if any)

    public ControllerUser() {
        this.setRole(Role.CONTROLLER);
    }
}

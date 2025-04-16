package org.example.yalla_api.admin.dtos.transfer;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.dtos.core.AreaDTO;
import org.example.yalla_api.common.entities.core.Area;

import java.time.LocalDateTime;

@Data
public class TransferRateDTO {

    private Long id;

    private AreaDTO sourceArea;

    private AreaDTO destinationArea;

    private Double ratePerPerson;

    private Boolean isActive = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer release;

}

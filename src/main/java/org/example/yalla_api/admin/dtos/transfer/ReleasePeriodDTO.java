package org.example.yalla_api.admin.dtos.transfer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReleasePeriodDTO {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer releaseDays;

    private Boolean isGeneral;

}

package org.example.yalla_api.admin.dtos.transfer;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
public class BackToContractDTO {

    private LocalDate startDate;
    private LocalDate endDate;

}

package org.example.yalla_api.admin.dtos.transfer;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TestBookingRequestDTO {

  @NotNull(message = "Start area is required")
  @Positive
  private Long startAreaId;

  @NotNull(message = "End area is required")
  @Positive
  private Long endAreaId;

  @NotNull(message = "Number of adults is required")
  @Min(value = 1, message = "At least one adult must be present")
  private Integer adults;

  // Each child's age must be between 0–17?
  @Size(max = 10, message = "Maximum 10 children allowed") // optional limit
  private List<@Min(0) @Max(value = 17, message = "Child Age is till 17 years") Integer> child = new ArrayList<>();

  @NotNull(message = "Start date is required")
  @Future(message = "Travel date must be in the future")
  private LocalDate startDate;

  // Each key = quantity (min 1), each value = extraId (positive long)
  private Map<@Min(1) Integer, @NotNull @Positive Long> extras = new HashMap<>();

}

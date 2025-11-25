package com.hervodalabs.formidable.commands;

import com.hervodalabs.formidable.domain.ProjectStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectForm {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private ProjectStatus status;

    @NotNull
    private Long contractorCompanyId;

    @NotNull
    private Long clientId;

    @NotNull(message = "Il prezzo è obbligatorio")
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di zero")
    private BigDecimal dailyRate;

    private List<Long> technologyIds = new ArrayList<>();

    private List<String> usages = new ArrayList<>();
}

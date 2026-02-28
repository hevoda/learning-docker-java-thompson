package com.hervodalabs.formidable.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProjectTechnology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Technology technology;

    private String usage; // <-- Qui metti "versionamento", "frontend", "CI/CD", ecc.
}

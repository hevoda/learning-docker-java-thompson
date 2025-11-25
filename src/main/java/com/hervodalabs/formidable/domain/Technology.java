package com.hervodalabs.formidable.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String version;

    @OneToMany(mappedBy = "technology")
    private Set<ProjectTechnology> projects = new java.util.HashSet<>();


}
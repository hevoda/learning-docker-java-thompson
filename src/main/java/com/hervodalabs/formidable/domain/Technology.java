package com.hervodalabs.formidable.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@ToString(exclude = "projects")
@EqualsAndHashCode(exclude = "projects")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String version;

    @OneToMany(mappedBy = "technology")
    private Set<ProjectTechnology> projects = new HashSet<>();
}
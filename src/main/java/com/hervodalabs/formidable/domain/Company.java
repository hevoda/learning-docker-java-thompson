package com.hervodalabs.formidable.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "contactPersons")
@EqualsAndHashCode(exclude = "contactPersons")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "company")
    private List<ContactPerson> contactPersons;
}
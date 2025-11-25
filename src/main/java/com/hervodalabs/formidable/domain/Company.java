package com.hervodalabs.formidable.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificativo univoco dell'azienda

    private String name;  // Nome dell'azienda

    private String address;  // Indirizzo dell'azienda (opzionale)

    private String phoneNumber;  // Numero di telefono dell'azienda (opzionale)

    private String email;  // Email dell'azienda (opzionale)

    // Relazione OneToMany con ContactPerson (un'azienda può avere più persone di contatto)
    @OneToMany(mappedBy = "company")  // 'company' è il campo della classe ContactPerson
    private List<ContactPerson> contactPersons;  // Lista delle persone di contatto per questa azienda

    // Altri campi utili
}

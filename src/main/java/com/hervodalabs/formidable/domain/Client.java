package com.hervodalabs.formidable.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificativo univoco del cliente

    private String name;

    private String address;

    // Relazione OneToOne con ContactPerson (un cliente ha una sola persona di contatto)
    @OneToOne
    private ContactPerson contactPerson;  // Persona di riferimento per il cliente

    private String phoneNumber;  // Numero di telefono del cliente (opzionale)

    private String email;  // Email del cliente (opzionale)

    // Altri campi utili
}

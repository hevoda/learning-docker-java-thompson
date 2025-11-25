package com.hervodalabs.formidable.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificativo univoco della persona di contatto

    private String firstName;  // Nome della persona di contatto

    private String lastName;  // Cognome della persona di contatto

    private String phoneNumber;  // Numero di telefono della persona di contatto

    private String email;  // Email della persona di contatto

    private String role;  // Ruolo della persona (ad esempio "Responsabile commerciale")

    // Relazione ManyToOne con Company (una persona di contatto appartiene a una sola azienda)
    @ManyToOne
    private Company company;  // Azienda di riferimento per questa persona di contatto

    // Altri campi opzionali
}

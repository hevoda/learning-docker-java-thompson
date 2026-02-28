package com.hervodalabs.formidable.repositories;

import com.hervodalabs.formidable.domain.ContactPerson;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Profile("h2")
@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {
}

package com.hervodalabs.formidable.repositories;

import com.hervodalabs.formidable.domain.Technology;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("h2")
@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}

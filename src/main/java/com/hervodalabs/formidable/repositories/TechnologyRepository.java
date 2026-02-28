package com.hervodalabs.formidable.repositories;

import com.hervodalabs.formidable.domain.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}

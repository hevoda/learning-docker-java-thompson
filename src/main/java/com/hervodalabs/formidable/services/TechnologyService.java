package com.hervodalabs.formidable.services;

import com.hervodalabs.formidable.domain.Technology;

import java.util.List;

public interface TechnologyService {
    Technology getById(Long id);
    List<Technology> listAll();
    Technology saveOrUpdate(Technology technology);
    void delete(Long id);
}

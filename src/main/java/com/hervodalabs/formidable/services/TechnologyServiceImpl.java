package com.hervodalabs.formidable.services;

import com.hervodalabs.formidable.domain.Technology;
import com.hervodalabs.formidable.repositories.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyServiceImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public Technology getById(Long id) {
        return technologyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Technology> listAll() {
        return technologyRepository.findAll();
    }

    @Override
    public Technology saveOrUpdate(Technology technology) {
        return technologyRepository.save(technology);
    }

    @Override
    public void delete(Long id) {
        technologyRepository.deleteById(id);
    }
}

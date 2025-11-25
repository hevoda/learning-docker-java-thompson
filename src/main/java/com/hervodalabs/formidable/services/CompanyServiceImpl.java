package com.hervodalabs.formidable.services;

import com.hervodalabs.formidable.domain.Client;
import com.hervodalabs.formidable.domain.Company;
import com.hervodalabs.formidable.repositories.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Profile("h2")
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    @Override
    public List<Company> listAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company getById(Long id) {
        Optional<Company> client = companyRepository.findById(id);  // Recupera il cliente per ID
        return client.orElse(null);  // Restituisce il cliente se presente, altrimenti null
    }
}

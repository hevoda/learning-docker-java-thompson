package com.hervodalabs.formidable.services;

import com.hervodalabs.formidable.domain.Client;
import com.hervodalabs.formidable.domain.Company;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CompanyService {

    List<Company> listAll();

    Company getById(Long id);
}

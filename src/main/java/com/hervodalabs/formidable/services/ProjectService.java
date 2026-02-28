package com.hervodalabs.formidable.services;

import com.hervodalabs.formidable.domain.Client;
import com.hervodalabs.formidable.domain.Company;
import com.hervodalabs.formidable.domain.Project;


import java.util.List;



public interface ProjectService {


    List<Project> listAll();


    Project getById(Long id);


    Project saveOrUpdateProject(Project project);


    void delete(Long id);


    List<Client> getAllClients();


    List<Company> getAllCompanies();
}

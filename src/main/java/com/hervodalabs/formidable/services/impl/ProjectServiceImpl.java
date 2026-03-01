package com.hervodalabs.formidable.services.impl;


import com.hervodalabs.formidable.domain.Client;
import com.hervodalabs.formidable.domain.Company;
import com.hervodalabs.formidable.domain.Project;
import com.hervodalabs.formidable.repositories.ProjectRepository;
import com.hervodalabs.formidable.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Profile("h2")
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientServiceImpl clientService;  // Iniezione del servizio ClientService
    private final CompanyServiceImpl companyService;


    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ClientServiceImpl clientService, CompanyServiceImpl companyService) {
        this.projectRepository = projectRepository;
        this.clientService = clientService;
        this.companyService = companyService;
    }


    @Override
    public List<Project> listAll() {
        log.debug("Fetching all projects from repository");
        List<Project> projects = projectRepository.findAll();
        log.info("Fetched {} projects", projects.size());
        return projects;
    }

    @Override
    public Project getById(Long id) {
        log.debug("Fetching project by ID: {}", id);
        return projectRepository.findById(id)
                .orElseGet(() -> {
                    log.warn("Project with ID {} not found", id);
                    return null;
                });
    }

    @Override
    public Project saveOrUpdateProject(Project project) {
        log.debug("Saving/updating project: {}", project.getName());
        Project saved = projectRepository.save(project);
        log.info("Saved project with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public void delete(Long id) {
        log.debug("Deleting project with ID: {}", id);
        projectRepository.deleteById(id);
        log.info("Deleted project with ID: {}", id);
    }

    @Override
    public List<Client> getAllClients() {
        log.debug("Fetching all clients");
        return clientService.listAll();
    }

    @Override
    public List<Company> getAllCompanies() {
        log.debug("Fetching all companies");
        return companyService.listAll();
    }
}

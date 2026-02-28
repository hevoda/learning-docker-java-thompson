package com.hervodalabs.formidable.converters;

import com.hervodalabs.formidable.commands.ProjectForm;
import com.hervodalabs.formidable.domain.*;
import com.hervodalabs.formidable.repositories.TechnologyRepository;
import com.hervodalabs.formidable.services.ClientService;
import com.hervodalabs.formidable.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProjectFormToProject {

    private final ClientService clientService;
    private final CompanyService companyService;
    private final TechnologyRepository technologyRepository;

    @Autowired
    public ProjectFormToProject(
            ClientService clientService,
            CompanyService companyService,
            TechnologyRepository technologyRepository
    ) {
        this.clientService = clientService;
        this.companyService = companyService;
        this.technologyRepository = technologyRepository;
    }

    public Project convert(ProjectForm form) {

        Project project = new Project();

        // --- CAMPi BASE ---
        project.setId(form.getId());
        project.setName(form.getName());
        project.setDescription(form.getDescription());
        project.setStartDate(form.getStartDate());
        project.setEndDate(form.getEndDate());
        project.setStatus(form.getStatus());
        project.setDailyRate(form.getDailyRate());

        // --- CLIENT & COMPANY ---
        project.setClient(clientService.getById(form.getClientId()));
        project.setContractorCompany(companyService.getById(form.getContractorCompanyId()));

        // --- TECNOLOGIE + USAGE ---
        Set<ProjectTechnology> techLinks = new HashSet<>();

        for (int i = 0; i < form.getTechnologyIds().size(); i++) {

            Long techId = form.getTechnologyIds().get(i);
            String usage = form.getUsages().get(i);

            Technology tech = technologyRepository.findById(techId)
                    .orElseThrow(() -> new RuntimeException("Technology ID not found: " + techId));

            ProjectTechnology link = new ProjectTechnology();
            link.setProject(project);
            link.setTechnology(tech);
            link.setUsage(usage);

            techLinks.add(link);
        }

        project.setTechnologies(techLinks);

        return project;
    }
}

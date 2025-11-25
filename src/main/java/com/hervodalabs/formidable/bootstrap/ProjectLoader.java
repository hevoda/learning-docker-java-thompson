package com.hervodalabs.formidable.bootstrap;


import com.hervodalabs.formidable.domain.*;
import com.hervodalabs.formidable.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Slf4j
@Profile("h2")
public class ProjectLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;
    private final TechnologyRepository technologyRepository;
    private final ProjectTechnologyRepository projectTechnologyRepository;

    @Autowired
    public ProjectLoader(ProjectRepository projectRepository, CompanyRepository companyRepository, ClientRepository clientRepository, TechnologyRepository technologyRepository, ProjectTechnologyRepository projectTechnologyRepository) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.technologyRepository = technologyRepository;
        this.projectTechnologyRepository = projectTechnologyRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Creazione di una compagnia di esempio (contractor)
        Company contractorCompany = new Company();
        contractorCompany.setName("Tech Solutions Inc.");
        companyRepository.save(contractorCompany);
        log.info("Saved Contractor Company - id: " + contractorCompany.getId());

        // Creazione di un cliente di esempio (client)
        Client client = new Client();
        client.setName("Acme Corp.");
        clientRepository.save(client);
        log.info("Saved Client - id: " + client.getId());

        // --- TECHNOLOGIES ---
        Technology springBoot = new Technology();
        springBoot.setName("Spring Boot");
        springBoot.setVersion("3.2");
        technologyRepository.save(springBoot);

        Technology git = new Technology();
        git.setName("Git");
        git.setVersion("2.42");
        technologyRepository.save(git);

        // Creazione di un progetto di esempio
        Project project1 = new Project();
        project1.setName("Spring Boot Migration");
        project1.setDescription("Migrating legacy systems to Spring Boot.");
        project1.setStartDate(LocalDate.of(2023, 10, 1));
        project1.setEndDate(LocalDate.of(2024, 4, 30));
        project1.setStatus(ProjectStatus.IN_PROGRESS);
        project1.setContractorCompany(contractorCompany);  // Associa il contractor
        project1.setClient(client);  // Associa il client
        project1.setDailyRate(new BigDecimal("750.00"));
        projectRepository.save(project1);
        log.info("Saved Project - id: " + project1.getId());

        // Spring Boot usage
        ProjectTechnology pt1 = new ProjectTechnology();
        pt1.setProject(project1);
        pt1.setTechnology(springBoot);
        pt1.setUsage("Backend Framework");
        projectTechnologyRepository.save(pt1);

        project1.getTechnologies().add(pt1);

        // Git usage
        ProjectTechnology pt2 = new ProjectTechnology();
        pt2.setProject(project1);
        pt2.setTechnology(git);
        pt2.setUsage("Versioning");
        projectTechnologyRepository.save(pt2);

        project1.getTechnologies().add(pt2);

        // Creazione di un altro progetto di esempio
        Project project2 = new Project();
        project2.setName("Cloud Infrastructure Setup");
        project2.setDescription("Setting up cloud infrastructure for scalable systems.");
        project2.setStartDate(LocalDate.of(2023, 9, 15));
        project2.setEndDate(LocalDate.of(2024, 1, 15));
        project2.setStatus(ProjectStatus.COMPLETED);
        project2.setContractorCompany(contractorCompany);  // Associa il contractor
        project2.setClient(client);  // Associa il client
        project2.setDailyRate(new BigDecimal("900.00"));

        projectRepository.save(project2);
        log.info("Saved Project - id: " + project2.getId());
        ProjectTechnology pt3 = new ProjectTechnology();
        pt3.setProject(project2);
        pt3.setTechnology(git);
        pt3.setUsage("CI/CD Pipeline");
        projectTechnologyRepository.save(pt3);

        project2.getTechnologies().add(pt3);

        log.info("Data loaded successfully!");

    }
}

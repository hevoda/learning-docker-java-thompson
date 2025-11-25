package com.hervodalabs.formidable.converters;

import com.hervodalabs.formidable.domain.Project;
import com.hervodalabs.formidable.domain.ProjectTechnology;
import com.hervodalabs.formidable.commands.ProjectForm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectToProjectForm {

    // Conversione da Project → ProjectForm
    public ProjectForm convert(Project project) {

        ProjectForm form = new ProjectForm();

        // --- CAMPI BASE ---
        form.setId(project.getId());
        form.setName(project.getName());
        form.setDescription(project.getDescription());
        form.setStartDate(project.getStartDate());
        form.setEndDate(project.getEndDate());
        form.setStatus(project.getStatus());
        form.setDailyRate(project.getDailyRate());

        // --- CLIENT & COMPANY ---
        if (project.getClient() != null) {
            form.setClientId(project.getClient().getId());
        }

        if (project.getContractorCompany() != null) {
            form.setContractorCompanyId(project.getContractorCompany().getId());
        }

        // --- TECNOLOGIE + USAGE ---
        List<Long> techIds = new ArrayList<>();
        List<String> usages = new ArrayList<>();

        for (ProjectTechnology pt : project.getTechnologies()) {
            techIds.add(pt.getTechnology().getId());
            usages.add(pt.getUsage());
        }

        form.setTechnologyIds(techIds);
        form.setUsages(usages);

        return form;
    }
}

package com.hervodalabs.formidable.controllers;

import com.hervodalabs.formidable.commands.ProjectForm;
import com.hervodalabs.formidable.converters.ProjectFormToProject;
import com.hervodalabs.formidable.converters.ProjectToProjectForm;
import com.hervodalabs.formidable.domain.Project;
import com.hervodalabs.formidable.domain.ProjectStatus;
import com.hervodalabs.formidable.services.ClientService;
import com.hervodalabs.formidable.services.CompanyService;
import com.hervodalabs.formidable.services.ProjectService;
import com.hervodalabs.formidable.services.TechnologyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@Profile("h2")
@RequestMapping("/projects")
public class ProjectController {

    private static final String FORM_VIEW = "project/projectform";
    private static final String LIST_VIEW = "project/list";
    private static final String SHOW_VIEW = "project/show";
    private static final String REDIRECT_LIST = "redirect:/projects/list";

    private final ProjectService projectService;
    private final ProjectToProjectForm projectToForm;
    private final ProjectFormToProject formToProject;
    private final ClientService clientService;
    private final CompanyService companyService;
    private final TechnologyService technologyService;

    public ProjectController(ProjectService projectService, ProjectToProjectForm projectToForm,
                             ProjectFormToProject formToProject, ClientService clientService,
                             CompanyService companyService, TechnologyService technologyService) {
        this.projectService = projectService;
        this.projectToForm = projectToForm;
        this.formToProject = formToProject;
        this.clientService = clientService;
        this.companyService = companyService;
        this.technologyService = technologyService;
    }

    // ================================
    // LISTA PROGETTI
    // ================================
    @GetMapping("/list")
    public String list(Model model) {
        log.debug("Listing all projects");
        List<Project> projects = projectService.listAll();
        model.addAttribute("projects", projects);
        return LIST_VIEW;
    }

    // ================================
    // DETTAGLI PROGETTO
    // ================================
    @GetMapping("/show/{id}")
    public String show(@PathVariable Long id, Model model, RedirectAttributes ra) {
        log.debug("Request to show project with ID: {}", id);
        Project project = projectService.getById(id);
        if (project == null) {
            log.warn("Project with ID {} not found", id);
            ra.addFlashAttribute("errorMessage", "Project not found");
            return REDIRECT_LIST;
        }
        model.addAttribute("project", project);
        return SHOW_VIEW;
    }

    // ================================
    // NUOVO PROGETTO
    // ================================
    @GetMapping("/new")
    public String newProject(Model model) {
        log.debug("Creating new project form");
        ProjectForm projectForm = new ProjectForm();
        model.addAttribute("projectForm", projectForm);
        populateReferenceData(model);
        return FORM_VIEW;
    }

    // ================================
    // MODIFICA PROGETTO
    // ================================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes ra) {
        log.debug("Request to edit project with ID: {}", id);
        Project project = projectService.getById(id);
        if (project == null) {
            log.warn("Project with ID {} not found", id);
            ra.addFlashAttribute("errorMessage", "Project not found");
            return REDIRECT_LIST;
        }
        ProjectForm form = projectToForm.convert(project);
        model.addAttribute("projectForm", form);
        populateReferenceData(model);
        return FORM_VIEW;
    }

    // ================================
    // SALVA/AGGIORNA PROGETTO
    // ================================
    @PostMapping
    public String saveOrUpdate(@Valid @ModelAttribute("projectForm") ProjectForm projectForm,
                               BindingResult result, RedirectAttributes ra, Model model) {

        if (result.hasErrors()) {
            log.warn("Validation errors: {}", result.getAllErrors());
            populateReferenceData(model);
            return FORM_VIEW;
        }

        Project project = formToProject.convert(projectForm);
        Project savedProject = projectService.saveOrUpdateProject(project);
        log.info("Saved project with ID: {}", savedProject.getId());
        ra.addFlashAttribute("successMessage", "Project saved successfully!");
        return "redirect:/projects/show/" + savedProject.getId();
    }

    // ================================
    // ELIMINA PROGETTO
    // ================================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        log.debug("Request to delete project with ID: {}", id);
        Project project = projectService.getById(id);
        if (project != null) {
            projectService.delete(id);
            ra.addFlashAttribute("successMessage", "Project deleted successfully!");
        } else {
            ra.addFlashAttribute("errorMessage", "Project not found");
        }
        return REDIRECT_LIST;
    }

    // ================================
    // UTILITY: popola liste per il form
    // ================================
    private void populateReferenceData(Model model) {
        model.addAttribute("allClients", clientService.listAll());
        model.addAttribute("allCompanies", companyService.listAll());
        model.addAttribute("allTechnologies", technologyService.listAll());
        model.addAttribute("statuses", ProjectStatus.values());
    }
}

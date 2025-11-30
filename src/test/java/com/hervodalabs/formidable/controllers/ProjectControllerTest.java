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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class ProjectControllerTest {

    @Mock
    ProjectService projectService;

    @Mock
    ProjectToProjectForm projectToProjectForm;

    @Mock
    ProjectFormToProject projectFormToProject;

    @Mock
    ClientService clientService;

    @Mock
    CompanyService companyService;

    @Mock
    TechnologyService technologyService;

    @Mock
    Model model;

    @Mock
    RedirectAttributes redirectAttributes;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list_ReturnsListViewWithProjects() {
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectService.listAll()).thenReturn(projects);

        String view = projectController.list(model);

        verify(model).addAttribute("projects", projects);
        assertEquals("project/list", view);
    }

    @Test
    void show_ReturnsShowView_WhenProjectExists() {
        Project project = new Project();
        project.setId(1L);
        when(projectService.getById(1L)).thenReturn(project);

        String view = projectController.show(1L, model, redirectAttributes);

        verify(model).addAttribute("project", project);
        assertEquals("project/show", view);
    }

    @Test
    void show_RedirectsToList_WhenProjectNotFound() {
        when(projectService.getById(1L)).thenReturn(null);

        String view = projectController.show(1L, model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute(eq("errorMessage"), anyString());
        assertEquals("redirect:/project/list", view);
    }

    @Test
    void newProject_ReturnsFormViewWithEmptyForm() {
        String view = projectController.newProject(model);

        ArgumentCaptor<ProjectForm> captor = ArgumentCaptor.forClass(ProjectForm.class);
        verify(model).addAttribute(eq("projectForm"), captor.capture());
        assertNotNull(captor.getValue());
        assertEquals("project/projectform", view);
    }

    @Test
    void edit_ReturnsFormView_WhenProjectExists() {
        Project project = new Project();
        project.setId(1L);
        ProjectForm form = new ProjectForm();
        when(projectService.getById(1L)).thenReturn(project);
        when(projectToProjectForm.convert(project)).thenReturn(form);

        String view = projectController.edit(1L, model, redirectAttributes);

        verify(model).addAttribute("projectForm", form);
        assertEquals("project/projectform", view);
    }

    @Test
    void edit_RedirectsToList_WhenProjectNotFound() {
        when(projectService.getById(1L)).thenReturn(null);

        String view = projectController.edit(1L, model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute(eq("errorMessage"), anyString());
        assertEquals("redirect:/project/list", view);
    }

    @Test
    void saveOrUpdate_ReturnsFormView_WhenValidationFails() {
        ProjectForm form = new ProjectForm();
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = projectController.saveOrUpdate(form, bindingResult, redirectAttributes, model);

        assertEquals("project/projectform", view);
    }

    @Test
    void saveOrUpdate_RedirectsToShow_WhenValidationPasses() {
        ProjectForm form = new ProjectForm();
        Project project = new Project();
        project.setId(1L);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(projectFormToProject.convert(form)).thenReturn(project);
        when(projectService.saveOrUpdateProject(project)).thenReturn(project);

        String view = projectController.saveOrUpdate(form, bindingResult, redirectAttributes, model);

        verify(redirectAttributes).addFlashAttribute(eq("successMessage"), anyString());
        assertEquals("redirect:/projects/show/1", view);
    }

    @Test
    void delete_DeletesProject_WhenExists() {
        Project project = new Project();
        project.setId(1L);
        when(projectService.getById(1L)).thenReturn(project);

        String view = projectController.delete(1L, redirectAttributes);

        verify(projectService).delete(1L);
        verify(redirectAttributes).addFlashAttribute(eq("successMessage"), anyString());
        assertEquals("redirect:/project/list", view);
    }

    @Test
    void delete_RedirectsToList_WhenProjectNotFound() {
        when(projectService.getById(1L)).thenReturn(null);

        String view = projectController.delete(1L, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute(eq("errorMessage"), anyString());
        assertEquals("redirect:/project/list", view);
    }
}

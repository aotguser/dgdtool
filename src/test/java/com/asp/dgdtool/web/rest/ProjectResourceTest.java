package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Project;
import com.asp.dgdtool.repository.ProjectRepository;
import com.asp.dgdtool.repository.search.ProjectSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_REQUESTOR_ID = 1;
    private static final Integer UPDATED_REQUESTOR_ID = 2;

    private static final Integer DEFAULT_ASSOCIATED_PROJECT_ID = 1;
    private static final Integer UPDATED_ASSOCIATED_PROJECT_ID = 2;

    private static final Integer DEFAULT_INITIATIVE_ID = 1;
    private static final Integer UPDATED_INITIATIVE_ID = 2;

    private static final Integer DEFAULT_TICKET_ID = 1;
    private static final Integer UPDATED_TICKET_ID = 2;

    private static final Integer DEFAULT_SERVICE_ID = 1;
    private static final Integer UPDATED_SERVICE_ID = 2;

    private static final Integer DEFAULT_APP_ID = 1;
    private static final Integer UPDATED_APP_ID = 2;

    private static final Integer DEFAULT_PACKAGE_ID = 1;
    private static final Integer UPDATED_PACKAGE_ID = 2;
    private static final String DEFAULT_LEGACY_OWNER = "AAAAA";
    private static final String UPDATED_LEGACY_OWNER = "BBBBB";
    private static final String DEFAULT_BUSINESS_UNIT = "AAAAA";
    private static final String UPDATED_BUSINESS_UNIT = "BBBBB";

    private static final ZonedDateTime DEFAULT_PLANNED_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PLANNED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PLANNED_START_DATE_STR = dateTimeFormatter.format(DEFAULT_PLANNED_START_DATE);

    private static final ZonedDateTime DEFAULT_PLANNED_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PLANNED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PLANNED_END_DATE_STR = dateTimeFormatter.format(DEFAULT_PLANNED_END_DATE);
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_STATUS_ID = 1;
    private static final Integer UPDATED_STATUS_ID = 2;
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_MODIFIED_BY = "AAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectSearchRepository projectSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectMockMvc;

    private Project project;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectResource projectResource = new ProjectResource();
        ReflectionTestUtils.setField(projectResource, "projectRepository", projectRepository);
        ReflectionTestUtils.setField(projectResource, "projectSearchRepository", projectSearchRepository);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        project = new Project();
        project.setRequestor_id(DEFAULT_REQUESTOR_ID);
        project.setAssociated_project_id(DEFAULT_ASSOCIATED_PROJECT_ID);
        project.setInitiative_id(DEFAULT_INITIATIVE_ID);
        project.setTicket_id(DEFAULT_TICKET_ID);
        project.setService_id(DEFAULT_SERVICE_ID);
        project.setApp_id(DEFAULT_APP_ID);
        project.setPackage_id(DEFAULT_PACKAGE_ID);
        project.setLegacy_owner(DEFAULT_LEGACY_OWNER);
        project.setBusiness_unit(DEFAULT_BUSINESS_UNIT);
        project.setPlanned_start_date(DEFAULT_PLANNED_START_DATE);
        project.setPlanned_end_date(DEFAULT_PLANNED_END_DATE);
        project.setDescription(DEFAULT_DESCRIPTION);
        project.setStatus_id(DEFAULT_STATUS_ID);
        project.setCreated_by(DEFAULT_CREATED_BY);
        project.setCreated_date(DEFAULT_CREATED_DATE);
        project.setModified_by(DEFAULT_MODIFIED_BY);
        project.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projects.get(projects.size() - 1);
        assertThat(testProject.getRequestor_id()).isEqualTo(DEFAULT_REQUESTOR_ID);
        assertThat(testProject.getAssociated_project_id()).isEqualTo(DEFAULT_ASSOCIATED_PROJECT_ID);
        assertThat(testProject.getInitiative_id()).isEqualTo(DEFAULT_INITIATIVE_ID);
        assertThat(testProject.getTicket_id()).isEqualTo(DEFAULT_TICKET_ID);
        assertThat(testProject.getService_id()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testProject.getApp_id()).isEqualTo(DEFAULT_APP_ID);
        assertThat(testProject.getPackage_id()).isEqualTo(DEFAULT_PACKAGE_ID);
        assertThat(testProject.getLegacy_owner()).isEqualTo(DEFAULT_LEGACY_OWNER);
        assertThat(testProject.getBusiness_unit()).isEqualTo(DEFAULT_BUSINESS_UNIT);
        assertThat(testProject.getPlanned_start_date()).isEqualTo(DEFAULT_PLANNED_START_DATE);
        assertThat(testProject.getPlanned_end_date()).isEqualTo(DEFAULT_PLANNED_END_DATE);
        assertThat(testProject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProject.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testProject.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProject.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProject.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testProject.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkRequestor_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setRequestor_id(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setDescription(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setStatus_id(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setCreated_by(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setCreated_date(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setModified_by(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setModified_date(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projects
        restProjectMockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
                .andExpect(jsonPath("$.[*].requestor_id").value(hasItem(DEFAULT_REQUESTOR_ID)))
                .andExpect(jsonPath("$.[*].associated_project_id").value(hasItem(DEFAULT_ASSOCIATED_PROJECT_ID)))
                .andExpect(jsonPath("$.[*].initiative_id").value(hasItem(DEFAULT_INITIATIVE_ID)))
                .andExpect(jsonPath("$.[*].ticket_id").value(hasItem(DEFAULT_TICKET_ID)))
                .andExpect(jsonPath("$.[*].service_id").value(hasItem(DEFAULT_SERVICE_ID)))
                .andExpect(jsonPath("$.[*].app_id").value(hasItem(DEFAULT_APP_ID)))
                .andExpect(jsonPath("$.[*].package_id").value(hasItem(DEFAULT_PACKAGE_ID)))
                .andExpect(jsonPath("$.[*].legacy_owner").value(hasItem(DEFAULT_LEGACY_OWNER.toString())))
                .andExpect(jsonPath("$.[*].business_unit").value(hasItem(DEFAULT_BUSINESS_UNIT.toString())))
                .andExpect(jsonPath("$.[*].planned_start_date").value(hasItem(DEFAULT_PLANNED_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].planned_end_date").value(hasItem(DEFAULT_PLANNED_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.requestor_id").value(DEFAULT_REQUESTOR_ID))
            .andExpect(jsonPath("$.associated_project_id").value(DEFAULT_ASSOCIATED_PROJECT_ID))
            .andExpect(jsonPath("$.initiative_id").value(DEFAULT_INITIATIVE_ID))
            .andExpect(jsonPath("$.ticket_id").value(DEFAULT_TICKET_ID))
            .andExpect(jsonPath("$.service_id").value(DEFAULT_SERVICE_ID))
            .andExpect(jsonPath("$.app_id").value(DEFAULT_APP_ID))
            .andExpect(jsonPath("$.package_id").value(DEFAULT_PACKAGE_ID))
            .andExpect(jsonPath("$.legacy_owner").value(DEFAULT_LEGACY_OWNER.toString()))
            .andExpect(jsonPath("$.business_unit").value(DEFAULT_BUSINESS_UNIT.toString()))
            .andExpect(jsonPath("$.planned_start_date").value(DEFAULT_PLANNED_START_DATE_STR))
            .andExpect(jsonPath("$.planned_end_date").value(DEFAULT_PLANNED_END_DATE_STR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

		int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        project.setRequestor_id(UPDATED_REQUESTOR_ID);
        project.setAssociated_project_id(UPDATED_ASSOCIATED_PROJECT_ID);
        project.setInitiative_id(UPDATED_INITIATIVE_ID);
        project.setTicket_id(UPDATED_TICKET_ID);
        project.setService_id(UPDATED_SERVICE_ID);
        project.setApp_id(UPDATED_APP_ID);
        project.setPackage_id(UPDATED_PACKAGE_ID);
        project.setLegacy_owner(UPDATED_LEGACY_OWNER);
        project.setBusiness_unit(UPDATED_BUSINESS_UNIT);
        project.setPlanned_start_date(UPDATED_PLANNED_START_DATE);
        project.setPlanned_end_date(UPDATED_PLANNED_END_DATE);
        project.setDescription(UPDATED_DESCRIPTION);
        project.setStatus_id(UPDATED_STATUS_ID);
        project.setCreated_by(UPDATED_CREATED_BY);
        project.setCreated_date(UPDATED_CREATED_DATE);
        project.setModified_by(UPDATED_MODIFIED_BY);
        project.setModified_date(UPDATED_MODIFIED_DATE);

        restProjectMockMvc.perform(put("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project)))
                .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projects.get(projects.size() - 1);
        assertThat(testProject.getRequestor_id()).isEqualTo(UPDATED_REQUESTOR_ID);
        assertThat(testProject.getAssociated_project_id()).isEqualTo(UPDATED_ASSOCIATED_PROJECT_ID);
        assertThat(testProject.getInitiative_id()).isEqualTo(UPDATED_INITIATIVE_ID);
        assertThat(testProject.getTicket_id()).isEqualTo(UPDATED_TICKET_ID);
        assertThat(testProject.getService_id()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testProject.getApp_id()).isEqualTo(UPDATED_APP_ID);
        assertThat(testProject.getPackage_id()).isEqualTo(UPDATED_PACKAGE_ID);
        assertThat(testProject.getLegacy_owner()).isEqualTo(UPDATED_LEGACY_OWNER);
        assertThat(testProject.getBusiness_unit()).isEqualTo(UPDATED_BUSINESS_UNIT);
        assertThat(testProject.getPlanned_start_date()).isEqualTo(UPDATED_PLANNED_START_DATE);
        assertThat(testProject.getPlanned_end_date()).isEqualTo(UPDATED_PLANNED_END_DATE);
        assertThat(testProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProject.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testProject.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProject.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testProject.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

		int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Get the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeDelete - 1);
    }
}

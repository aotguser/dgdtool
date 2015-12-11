package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Project_team;
import com.asp.dgdtool.repository.Project_teamRepository;
import com.asp.dgdtool.repository.search.Project_teamSearchRepository;

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
 * Test class for the Project_teamResource REST controller.
 *
 * @see Project_teamResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Project_teamResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_PROJECT_ID = 1;
    private static final Integer UPDATED_PROJECT_ID = 2;

    private static final Integer DEFAULT_RESOURCE_ID = 1;
    private static final Integer UPDATED_RESOURCE_ID = 2;

    private static final Integer DEFAULT_ROLE_ID = 1;
    private static final Integer UPDATED_ROLE_ID = 2;

    private static final Integer DEFAULT_SUPPORT_LEVEL = 1;
    private static final Integer UPDATED_SUPPORT_LEVEL = 2;

    private static final Integer DEFAULT_EST_HOURS = 1;
    private static final Integer UPDATED_EST_HOURS = 2;

    private static final ZonedDateTime DEFAULT_ASSIGNED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ASSIGNED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ASSIGNED_DATE_STR = dateTimeFormatter.format(DEFAULT_ASSIGNED_DATE);
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
    private Project_teamRepository project_teamRepository;

    @Inject
    private Project_teamSearchRepository project_teamSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProject_teamMockMvc;

    private Project_team project_team;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Project_teamResource project_teamResource = new Project_teamResource();
        ReflectionTestUtils.setField(project_teamResource, "project_teamRepository", project_teamRepository);
        ReflectionTestUtils.setField(project_teamResource, "project_teamSearchRepository", project_teamSearchRepository);
        this.restProject_teamMockMvc = MockMvcBuilders.standaloneSetup(project_teamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        project_team = new Project_team();
        project_team.setProject_id(DEFAULT_PROJECT_ID);
        project_team.setResource_id(DEFAULT_RESOURCE_ID);
        project_team.setRole_id(DEFAULT_ROLE_ID);
        project_team.setSupport_level(DEFAULT_SUPPORT_LEVEL);
        project_team.setEst_hours(DEFAULT_EST_HOURS);
        project_team.setAssigned_date(DEFAULT_ASSIGNED_DATE);
        project_team.setDescription(DEFAULT_DESCRIPTION);
        project_team.setStatus_id(DEFAULT_STATUS_ID);
        project_team.setCreated_by(DEFAULT_CREATED_BY);
        project_team.setCreated_date(DEFAULT_CREATED_DATE);
        project_team.setModified_by(DEFAULT_MODIFIED_BY);
        project_team.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProject_team() throws Exception {
        int databaseSizeBeforeCreate = project_teamRepository.findAll().size();

        // Create the Project_team

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isCreated());

        // Validate the Project_team in the database
        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeCreate + 1);
        Project_team testProject_team = project_teams.get(project_teams.size() - 1);
        assertThat(testProject_team.getProject_id()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProject_team.getResource_id()).isEqualTo(DEFAULT_RESOURCE_ID);
        assertThat(testProject_team.getRole_id()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testProject_team.getSupport_level()).isEqualTo(DEFAULT_SUPPORT_LEVEL);
        assertThat(testProject_team.getEst_hours()).isEqualTo(DEFAULT_EST_HOURS);
        assertThat(testProject_team.getAssigned_date()).isEqualTo(DEFAULT_ASSIGNED_DATE);
        assertThat(testProject_team.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProject_team.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testProject_team.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProject_team.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProject_team.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testProject_team.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkProject_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setProject_id(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResource_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setResource_id(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRole_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setRole_id(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setDescription(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setStatus_id(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setCreated_by(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setCreated_date(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setModified_by(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_teamRepository.findAll().size();
        // set the field null
        project_team.setModified_date(null);

        // Create the Project_team, which fails.

        restProject_teamMockMvc.perform(post("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isBadRequest());

        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProject_teams() throws Exception {
        // Initialize the database
        project_teamRepository.saveAndFlush(project_team);

        // Get all the project_teams
        restProject_teamMockMvc.perform(get("/api/project_teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(project_team.getId().intValue())))
                .andExpect(jsonPath("$.[*].project_id").value(hasItem(DEFAULT_PROJECT_ID)))
                .andExpect(jsonPath("$.[*].resource_id").value(hasItem(DEFAULT_RESOURCE_ID)))
                .andExpect(jsonPath("$.[*].role_id").value(hasItem(DEFAULT_ROLE_ID)))
                .andExpect(jsonPath("$.[*].support_level").value(hasItem(DEFAULT_SUPPORT_LEVEL)))
                .andExpect(jsonPath("$.[*].est_hours").value(hasItem(DEFAULT_EST_HOURS)))
                .andExpect(jsonPath("$.[*].assigned_date").value(hasItem(DEFAULT_ASSIGNED_DATE_STR)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getProject_team() throws Exception {
        // Initialize the database
        project_teamRepository.saveAndFlush(project_team);

        // Get the project_team
        restProject_teamMockMvc.perform(get("/api/project_teams/{id}", project_team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(project_team.getId().intValue()))
            .andExpect(jsonPath("$.project_id").value(DEFAULT_PROJECT_ID))
            .andExpect(jsonPath("$.resource_id").value(DEFAULT_RESOURCE_ID))
            .andExpect(jsonPath("$.role_id").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.support_level").value(DEFAULT_SUPPORT_LEVEL))
            .andExpect(jsonPath("$.est_hours").value(DEFAULT_EST_HOURS))
            .andExpect(jsonPath("$.assigned_date").value(DEFAULT_ASSIGNED_DATE_STR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProject_team() throws Exception {
        // Get the project_team
        restProject_teamMockMvc.perform(get("/api/project_teams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject_team() throws Exception {
        // Initialize the database
        project_teamRepository.saveAndFlush(project_team);

		int databaseSizeBeforeUpdate = project_teamRepository.findAll().size();

        // Update the project_team
        project_team.setProject_id(UPDATED_PROJECT_ID);
        project_team.setResource_id(UPDATED_RESOURCE_ID);
        project_team.setRole_id(UPDATED_ROLE_ID);
        project_team.setSupport_level(UPDATED_SUPPORT_LEVEL);
        project_team.setEst_hours(UPDATED_EST_HOURS);
        project_team.setAssigned_date(UPDATED_ASSIGNED_DATE);
        project_team.setDescription(UPDATED_DESCRIPTION);
        project_team.setStatus_id(UPDATED_STATUS_ID);
        project_team.setCreated_by(UPDATED_CREATED_BY);
        project_team.setCreated_date(UPDATED_CREATED_DATE);
        project_team.setModified_by(UPDATED_MODIFIED_BY);
        project_team.setModified_date(UPDATED_MODIFIED_DATE);

        restProject_teamMockMvc.perform(put("/api/project_teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_team)))
                .andExpect(status().isOk());

        // Validate the Project_team in the database
        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeUpdate);
        Project_team testProject_team = project_teams.get(project_teams.size() - 1);
        assertThat(testProject_team.getProject_id()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProject_team.getResource_id()).isEqualTo(UPDATED_RESOURCE_ID);
        assertThat(testProject_team.getRole_id()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testProject_team.getSupport_level()).isEqualTo(UPDATED_SUPPORT_LEVEL);
        assertThat(testProject_team.getEst_hours()).isEqualTo(UPDATED_EST_HOURS);
        assertThat(testProject_team.getAssigned_date()).isEqualTo(UPDATED_ASSIGNED_DATE);
        assertThat(testProject_team.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProject_team.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testProject_team.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject_team.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProject_team.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testProject_team.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteProject_team() throws Exception {
        // Initialize the database
        project_teamRepository.saveAndFlush(project_team);

		int databaseSizeBeforeDelete = project_teamRepository.findAll().size();

        // Get the project_team
        restProject_teamMockMvc.perform(delete("/api/project_teams/{id}", project_team.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Project_team> project_teams = project_teamRepository.findAll();
        assertThat(project_teams).hasSize(databaseSizeBeforeDelete - 1);
    }
}

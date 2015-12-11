package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Project_phase;
import com.asp.dgdtool.repository.Project_phaseRepository;
import com.asp.dgdtool.repository.search.Project_phaseSearchRepository;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Project_phaseResource REST controller.
 *
 * @see Project_phaseResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Project_phaseResourceTest {

    private static final String DEFAULT_PROJECT_PHASE_NAME = "AAAAA";
    private static final String UPDATED_PROJECT_PHASE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_STATUS_ID = 1;
    private static final Integer UPDATED_STATUS_ID = 2;
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";
    private static final String DEFAULT_MODIFIED_BY = "AAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private Project_phaseRepository project_phaseRepository;

    @Inject
    private Project_phaseSearchRepository project_phaseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProject_phaseMockMvc;

    private Project_phase project_phase;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Project_phaseResource project_phaseResource = new Project_phaseResource();
        ReflectionTestUtils.setField(project_phaseResource, "project_phaseRepository", project_phaseRepository);
        ReflectionTestUtils.setField(project_phaseResource, "project_phaseSearchRepository", project_phaseSearchRepository);
        this.restProject_phaseMockMvc = MockMvcBuilders.standaloneSetup(project_phaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        project_phase = new Project_phase();
        project_phase.setProject_phase_name(DEFAULT_PROJECT_PHASE_NAME);
        project_phase.setDescription(DEFAULT_DESCRIPTION);
        project_phase.setStatus_id(DEFAULT_STATUS_ID);
        project_phase.setCreated_by(DEFAULT_CREATED_BY);
        project_phase.setModified_by(DEFAULT_MODIFIED_BY);
        project_phase.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProject_phase() throws Exception {
        int databaseSizeBeforeCreate = project_phaseRepository.findAll().size();

        // Create the Project_phase

        restProject_phaseMockMvc.perform(post("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isCreated());

        // Validate the Project_phase in the database
        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeCreate + 1);
        Project_phase testProject_phase = project_phases.get(project_phases.size() - 1);
        assertThat(testProject_phase.getProject_phase_name()).isEqualTo(DEFAULT_PROJECT_PHASE_NAME);
        assertThat(testProject_phase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProject_phase.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testProject_phase.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProject_phase.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testProject_phase.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkProject_phase_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_phaseRepository.findAll().size();
        // set the field null
        project_phase.setProject_phase_name(null);

        // Create the Project_phase, which fails.

        restProject_phaseMockMvc.perform(post("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isBadRequest());

        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_phaseRepository.findAll().size();
        // set the field null
        project_phase.setDescription(null);

        // Create the Project_phase, which fails.

        restProject_phaseMockMvc.perform(post("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isBadRequest());

        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_phaseRepository.findAll().size();
        // set the field null
        project_phase.setStatus_id(null);

        // Create the Project_phase, which fails.

        restProject_phaseMockMvc.perform(post("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isBadRequest());

        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_phaseRepository.findAll().size();
        // set the field null
        project_phase.setCreated_by(null);

        // Create the Project_phase, which fails.

        restProject_phaseMockMvc.perform(post("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isBadRequest());

        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_phaseRepository.findAll().size();
        // set the field null
        project_phase.setModified_by(null);

        // Create the Project_phase, which fails.

        restProject_phaseMockMvc.perform(post("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isBadRequest());

        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = project_phaseRepository.findAll().size();
        // set the field null
        project_phase.setModified_date(null);

        // Create the Project_phase, which fails.

        restProject_phaseMockMvc.perform(post("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isBadRequest());

        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProject_phases() throws Exception {
        // Initialize the database
        project_phaseRepository.saveAndFlush(project_phase);

        // Get all the project_phases
        restProject_phaseMockMvc.perform(get("/api/project_phases"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(project_phase.getId().intValue())))
                .andExpect(jsonPath("$.[*].project_phase_name").value(hasItem(DEFAULT_PROJECT_PHASE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getProject_phase() throws Exception {
        // Initialize the database
        project_phaseRepository.saveAndFlush(project_phase);

        // Get the project_phase
        restProject_phaseMockMvc.perform(get("/api/project_phases/{id}", project_phase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(project_phase.getId().intValue()))
            .andExpect(jsonPath("$.project_phase_name").value(DEFAULT_PROJECT_PHASE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProject_phase() throws Exception {
        // Get the project_phase
        restProject_phaseMockMvc.perform(get("/api/project_phases/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject_phase() throws Exception {
        // Initialize the database
        project_phaseRepository.saveAndFlush(project_phase);

		int databaseSizeBeforeUpdate = project_phaseRepository.findAll().size();

        // Update the project_phase
        project_phase.setProject_phase_name(UPDATED_PROJECT_PHASE_NAME);
        project_phase.setDescription(UPDATED_DESCRIPTION);
        project_phase.setStatus_id(UPDATED_STATUS_ID);
        project_phase.setCreated_by(UPDATED_CREATED_BY);
        project_phase.setModified_by(UPDATED_MODIFIED_BY);
        project_phase.setModified_date(UPDATED_MODIFIED_DATE);

        restProject_phaseMockMvc.perform(put("/api/project_phases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(project_phase)))
                .andExpect(status().isOk());

        // Validate the Project_phase in the database
        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeUpdate);
        Project_phase testProject_phase = project_phases.get(project_phases.size() - 1);
        assertThat(testProject_phase.getProject_phase_name()).isEqualTo(UPDATED_PROJECT_PHASE_NAME);
        assertThat(testProject_phase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProject_phase.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testProject_phase.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject_phase.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testProject_phase.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteProject_phase() throws Exception {
        // Initialize the database
        project_phaseRepository.saveAndFlush(project_phase);

		int databaseSizeBeforeDelete = project_phaseRepository.findAll().size();

        // Get the project_phase
        restProject_phaseMockMvc.perform(delete("/api/project_phases/{id}", project_phase.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Project_phase> project_phases = project_phaseRepository.findAll();
        assertThat(project_phases).hasSize(databaseSizeBeforeDelete - 1);
    }
}

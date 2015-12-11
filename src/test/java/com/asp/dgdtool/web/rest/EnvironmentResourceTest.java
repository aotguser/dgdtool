package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Environment;
import com.asp.dgdtool.repository.EnvironmentRepository;
import com.asp.dgdtool.repository.search.EnvironmentSearchRepository;

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
 * Test class for the EnvironmentResource REST controller.
 *
 * @see EnvironmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EnvironmentResourceTest {

    private static final String DEFAULT_ENVIRONMENT_NAME = "AAAAA";
    private static final String UPDATED_ENVIRONMENT_NAME = "BBBBB";
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
    private EnvironmentRepository environmentRepository;

    @Inject
    private EnvironmentSearchRepository environmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEnvironmentMockMvc;

    private Environment environment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnvironmentResource environmentResource = new EnvironmentResource();
        ReflectionTestUtils.setField(environmentResource, "environmentRepository", environmentRepository);
        ReflectionTestUtils.setField(environmentResource, "environmentSearchRepository", environmentSearchRepository);
        this.restEnvironmentMockMvc = MockMvcBuilders.standaloneSetup(environmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        environment = new Environment();
        environment.setEnvironment_name(DEFAULT_ENVIRONMENT_NAME);
        environment.setDescription(DEFAULT_DESCRIPTION);
        environment.setStatus_id(DEFAULT_STATUS_ID);
        environment.setCreated_by(DEFAULT_CREATED_BY);
        environment.setCreated_date(DEFAULT_CREATED_DATE);
        environment.setModified_by(DEFAULT_MODIFIED_BY);
        environment.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createEnvironment() throws Exception {
        int databaseSizeBeforeCreate = environmentRepository.findAll().size();

        // Create the Environment

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isCreated());

        // Validate the Environment in the database
        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeCreate + 1);
        Environment testEnvironment = environments.get(environments.size() - 1);
        assertThat(testEnvironment.getEnvironment_name()).isEqualTo(DEFAULT_ENVIRONMENT_NAME);
        assertThat(testEnvironment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEnvironment.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testEnvironment.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEnvironment.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEnvironment.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEnvironment.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkEnvironment_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setEnvironment_name(null);

        // Create the Environment, which fails.

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isBadRequest());

        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setDescription(null);

        // Create the Environment, which fails.

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isBadRequest());

        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setStatus_id(null);

        // Create the Environment, which fails.

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isBadRequest());

        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setCreated_by(null);

        // Create the Environment, which fails.

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isBadRequest());

        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setCreated_date(null);

        // Create the Environment, which fails.

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isBadRequest());

        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setModified_by(null);

        // Create the Environment, which fails.

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isBadRequest());

        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setModified_date(null);

        // Create the Environment, which fails.

        restEnvironmentMockMvc.perform(post("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isBadRequest());

        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnvironments() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        // Get all the environments
        restEnvironmentMockMvc.perform(get("/api/environments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(environment.getId().intValue())))
                .andExpect(jsonPath("$.[*].environment_name").value(hasItem(DEFAULT_ENVIRONMENT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        // Get the environment
        restEnvironmentMockMvc.perform(get("/api/environments/{id}", environment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(environment.getId().intValue()))
            .andExpect(jsonPath("$.environment_name").value(DEFAULT_ENVIRONMENT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnvironment() throws Exception {
        // Get the environment
        restEnvironmentMockMvc.perform(get("/api/environments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

		int databaseSizeBeforeUpdate = environmentRepository.findAll().size();

        // Update the environment
        environment.setEnvironment_name(UPDATED_ENVIRONMENT_NAME);
        environment.setDescription(UPDATED_DESCRIPTION);
        environment.setStatus_id(UPDATED_STATUS_ID);
        environment.setCreated_by(UPDATED_CREATED_BY);
        environment.setCreated_date(UPDATED_CREATED_DATE);
        environment.setModified_by(UPDATED_MODIFIED_BY);
        environment.setModified_date(UPDATED_MODIFIED_DATE);

        restEnvironmentMockMvc.perform(put("/api/environments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(environment)))
                .andExpect(status().isOk());

        // Validate the Environment in the database
        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeUpdate);
        Environment testEnvironment = environments.get(environments.size() - 1);
        assertThat(testEnvironment.getEnvironment_name()).isEqualTo(UPDATED_ENVIRONMENT_NAME);
        assertThat(testEnvironment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnvironment.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testEnvironment.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEnvironment.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEnvironment.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEnvironment.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

		int databaseSizeBeforeDelete = environmentRepository.findAll().size();

        // Get the environment
        restEnvironmentMockMvc.perform(delete("/api/environments/{id}", environment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Environment> environments = environmentRepository.findAll();
        assertThat(environments).hasSize(databaseSizeBeforeDelete - 1);
    }
}

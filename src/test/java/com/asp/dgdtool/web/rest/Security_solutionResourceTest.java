package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Security_solution;
import com.asp.dgdtool.repository.Security_solutionRepository;
import com.asp.dgdtool.repository.search.Security_solutionSearchRepository;

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
 * Test class for the Security_solutionResource REST controller.
 *
 * @see Security_solutionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Security_solutionResourceTest {

    private static final String DEFAULT_SECURITY_SOLUTION_NAME = "AAAAA";
    private static final String UPDATED_SECURITY_SOLUTION_NAME = "BBBBB";
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
    private Security_solutionRepository security_solutionRepository;

    @Inject
    private Security_solutionSearchRepository security_solutionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSecurity_solutionMockMvc;

    private Security_solution security_solution;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Security_solutionResource security_solutionResource = new Security_solutionResource();
        ReflectionTestUtils.setField(security_solutionResource, "security_solutionRepository", security_solutionRepository);
        ReflectionTestUtils.setField(security_solutionResource, "security_solutionSearchRepository", security_solutionSearchRepository);
        this.restSecurity_solutionMockMvc = MockMvcBuilders.standaloneSetup(security_solutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        security_solution = new Security_solution();
        security_solution.setSecurity_solution_name(DEFAULT_SECURITY_SOLUTION_NAME);
        security_solution.setDescription(DEFAULT_DESCRIPTION);
        security_solution.setStatus_id(DEFAULT_STATUS_ID);
        security_solution.setCreated_by(DEFAULT_CREATED_BY);
        security_solution.setCreated_date(DEFAULT_CREATED_DATE);
        security_solution.setModified_by(DEFAULT_MODIFIED_BY);
        security_solution.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSecurity_solution() throws Exception {
        int databaseSizeBeforeCreate = security_solutionRepository.findAll().size();

        // Create the Security_solution

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isCreated());

        // Validate the Security_solution in the database
        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeCreate + 1);
        Security_solution testSecurity_solution = security_solutions.get(security_solutions.size() - 1);
        assertThat(testSecurity_solution.getSecurity_solution_name()).isEqualTo(DEFAULT_SECURITY_SOLUTION_NAME);
        assertThat(testSecurity_solution.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSecurity_solution.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testSecurity_solution.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSecurity_solution.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSecurity_solution.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSecurity_solution.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkSecurity_solution_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = security_solutionRepository.findAll().size();
        // set the field null
        security_solution.setSecurity_solution_name(null);

        // Create the Security_solution, which fails.

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isBadRequest());

        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = security_solutionRepository.findAll().size();
        // set the field null
        security_solution.setDescription(null);

        // Create the Security_solution, which fails.

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isBadRequest());

        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = security_solutionRepository.findAll().size();
        // set the field null
        security_solution.setStatus_id(null);

        // Create the Security_solution, which fails.

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isBadRequest());

        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = security_solutionRepository.findAll().size();
        // set the field null
        security_solution.setCreated_by(null);

        // Create the Security_solution, which fails.

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isBadRequest());

        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = security_solutionRepository.findAll().size();
        // set the field null
        security_solution.setCreated_date(null);

        // Create the Security_solution, which fails.

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isBadRequest());

        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = security_solutionRepository.findAll().size();
        // set the field null
        security_solution.setModified_by(null);

        // Create the Security_solution, which fails.

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isBadRequest());

        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = security_solutionRepository.findAll().size();
        // set the field null
        security_solution.setModified_date(null);

        // Create the Security_solution, which fails.

        restSecurity_solutionMockMvc.perform(post("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isBadRequest());

        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecurity_solutions() throws Exception {
        // Initialize the database
        security_solutionRepository.saveAndFlush(security_solution);

        // Get all the security_solutions
        restSecurity_solutionMockMvc.perform(get("/api/security_solutions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(security_solution.getId().intValue())))
                .andExpect(jsonPath("$.[*].security_solution_name").value(hasItem(DEFAULT_SECURITY_SOLUTION_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSecurity_solution() throws Exception {
        // Initialize the database
        security_solutionRepository.saveAndFlush(security_solution);

        // Get the security_solution
        restSecurity_solutionMockMvc.perform(get("/api/security_solutions/{id}", security_solution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(security_solution.getId().intValue()))
            .andExpect(jsonPath("$.security_solution_name").value(DEFAULT_SECURITY_SOLUTION_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecurity_solution() throws Exception {
        // Get the security_solution
        restSecurity_solutionMockMvc.perform(get("/api/security_solutions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecurity_solution() throws Exception {
        // Initialize the database
        security_solutionRepository.saveAndFlush(security_solution);

		int databaseSizeBeforeUpdate = security_solutionRepository.findAll().size();

        // Update the security_solution
        security_solution.setSecurity_solution_name(UPDATED_SECURITY_SOLUTION_NAME);
        security_solution.setDescription(UPDATED_DESCRIPTION);
        security_solution.setStatus_id(UPDATED_STATUS_ID);
        security_solution.setCreated_by(UPDATED_CREATED_BY);
        security_solution.setCreated_date(UPDATED_CREATED_DATE);
        security_solution.setModified_by(UPDATED_MODIFIED_BY);
        security_solution.setModified_date(UPDATED_MODIFIED_DATE);

        restSecurity_solutionMockMvc.perform(put("/api/security_solutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(security_solution)))
                .andExpect(status().isOk());

        // Validate the Security_solution in the database
        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeUpdate);
        Security_solution testSecurity_solution = security_solutions.get(security_solutions.size() - 1);
        assertThat(testSecurity_solution.getSecurity_solution_name()).isEqualTo(UPDATED_SECURITY_SOLUTION_NAME);
        assertThat(testSecurity_solution.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSecurity_solution.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testSecurity_solution.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSecurity_solution.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSecurity_solution.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSecurity_solution.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteSecurity_solution() throws Exception {
        // Initialize the database
        security_solutionRepository.saveAndFlush(security_solution);

		int databaseSizeBeforeDelete = security_solutionRepository.findAll().size();

        // Get the security_solution
        restSecurity_solutionMockMvc.perform(delete("/api/security_solutions/{id}", security_solution.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Security_solution> security_solutions = security_solutionRepository.findAll();
        assertThat(security_solutions).hasSize(databaseSizeBeforeDelete - 1);
    }
}

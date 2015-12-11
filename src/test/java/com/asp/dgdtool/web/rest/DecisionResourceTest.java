package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Decision;
import com.asp.dgdtool.repository.DecisionRepository;
import com.asp.dgdtool.repository.search.DecisionSearchRepository;

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
 * Test class for the DecisionResource REST controller.
 *
 * @see DecisionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DecisionResourceTest {

    private static final String DEFAULT_DECISION_NAME = "AAAAA";
    private static final String UPDATED_DECISION_NAME = "BBBBB";
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
    private DecisionRepository decisionRepository;

    @Inject
    private DecisionSearchRepository decisionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDecisionMockMvc;

    private Decision decision;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DecisionResource decisionResource = new DecisionResource();
        ReflectionTestUtils.setField(decisionResource, "decisionRepository", decisionRepository);
        ReflectionTestUtils.setField(decisionResource, "decisionSearchRepository", decisionSearchRepository);
        this.restDecisionMockMvc = MockMvcBuilders.standaloneSetup(decisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        decision = new Decision();
        decision.setDecision_name(DEFAULT_DECISION_NAME);
        decision.setDescription(DEFAULT_DESCRIPTION);
        decision.setStatus_id(DEFAULT_STATUS_ID);
        decision.setCreated_by(DEFAULT_CREATED_BY);
        decision.setCreated_date(DEFAULT_CREATED_DATE);
        decision.setModified_by(DEFAULT_MODIFIED_BY);
        decision.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDecision() throws Exception {
        int databaseSizeBeforeCreate = decisionRepository.findAll().size();

        // Create the Decision

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isCreated());

        // Validate the Decision in the database
        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeCreate + 1);
        Decision testDecision = decisions.get(decisions.size() - 1);
        assertThat(testDecision.getDecision_name()).isEqualTo(DEFAULT_DECISION_NAME);
        assertThat(testDecision.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDecision.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testDecision.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDecision.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDecision.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDecision.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkDecision_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionRepository.findAll().size();
        // set the field null
        decision.setDecision_name(null);

        // Create the Decision, which fails.

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isBadRequest());

        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionRepository.findAll().size();
        // set the field null
        decision.setDescription(null);

        // Create the Decision, which fails.

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isBadRequest());

        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionRepository.findAll().size();
        // set the field null
        decision.setStatus_id(null);

        // Create the Decision, which fails.

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isBadRequest());

        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionRepository.findAll().size();
        // set the field null
        decision.setCreated_by(null);

        // Create the Decision, which fails.

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isBadRequest());

        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionRepository.findAll().size();
        // set the field null
        decision.setCreated_date(null);

        // Create the Decision, which fails.

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isBadRequest());

        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionRepository.findAll().size();
        // set the field null
        decision.setModified_by(null);

        // Create the Decision, which fails.

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isBadRequest());

        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionRepository.findAll().size();
        // set the field null
        decision.setModified_date(null);

        // Create the Decision, which fails.

        restDecisionMockMvc.perform(post("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isBadRequest());

        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDecisions() throws Exception {
        // Initialize the database
        decisionRepository.saveAndFlush(decision);

        // Get all the decisions
        restDecisionMockMvc.perform(get("/api/decisions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(decision.getId().intValue())))
                .andExpect(jsonPath("$.[*].decision_name").value(hasItem(DEFAULT_DECISION_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDecision() throws Exception {
        // Initialize the database
        decisionRepository.saveAndFlush(decision);

        // Get the decision
        restDecisionMockMvc.perform(get("/api/decisions/{id}", decision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(decision.getId().intValue()))
            .andExpect(jsonPath("$.decision_name").value(DEFAULT_DECISION_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDecision() throws Exception {
        // Get the decision
        restDecisionMockMvc.perform(get("/api/decisions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDecision() throws Exception {
        // Initialize the database
        decisionRepository.saveAndFlush(decision);

		int databaseSizeBeforeUpdate = decisionRepository.findAll().size();

        // Update the decision
        decision.setDecision_name(UPDATED_DECISION_NAME);
        decision.setDescription(UPDATED_DESCRIPTION);
        decision.setStatus_id(UPDATED_STATUS_ID);
        decision.setCreated_by(UPDATED_CREATED_BY);
        decision.setCreated_date(UPDATED_CREATED_DATE);
        decision.setModified_by(UPDATED_MODIFIED_BY);
        decision.setModified_date(UPDATED_MODIFIED_DATE);

        restDecisionMockMvc.perform(put("/api/decisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(decision)))
                .andExpect(status().isOk());

        // Validate the Decision in the database
        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeUpdate);
        Decision testDecision = decisions.get(decisions.size() - 1);
        assertThat(testDecision.getDecision_name()).isEqualTo(UPDATED_DECISION_NAME);
        assertThat(testDecision.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDecision.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testDecision.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDecision.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDecision.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDecision.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteDecision() throws Exception {
        // Initialize the database
        decisionRepository.saveAndFlush(decision);

		int databaseSizeBeforeDelete = decisionRepository.findAll().size();

        // Get the decision
        restDecisionMockMvc.perform(delete("/api/decisions/{id}", decision.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Decision> decisions = decisionRepository.findAll();
        assertThat(decisions).hasSize(databaseSizeBeforeDelete - 1);
    }
}

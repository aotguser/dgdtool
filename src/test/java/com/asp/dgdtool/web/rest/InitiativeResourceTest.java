package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Initiative;
import com.asp.dgdtool.repository.InitiativeRepository;
import com.asp.dgdtool.repository.search.InitiativeSearchRepository;

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
 * Test class for the InitiativeResource REST controller.
 *
 * @see InitiativeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InitiativeResourceTest {

    private static final String DEFAULT_INITIATIVE_NAME = "AAAAA";
    private static final String UPDATED_INITIATIVE_NAME = "BBBBB";

    private static final Integer DEFAULT_INITIATIVE_TYPE_ID = 1;
    private static final Integer UPDATED_INITIATIVE_TYPE_ID = 2;
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
    private InitiativeRepository initiativeRepository;

    @Inject
    private InitiativeSearchRepository initiativeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInitiativeMockMvc;

    private Initiative initiative;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InitiativeResource initiativeResource = new InitiativeResource();
        ReflectionTestUtils.setField(initiativeResource, "initiativeRepository", initiativeRepository);
        ReflectionTestUtils.setField(initiativeResource, "initiativeSearchRepository", initiativeSearchRepository);
        this.restInitiativeMockMvc = MockMvcBuilders.standaloneSetup(initiativeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        initiative = new Initiative();
        initiative.setInitiative_name(DEFAULT_INITIATIVE_NAME);
        initiative.setInitiative_type_id(DEFAULT_INITIATIVE_TYPE_ID);
        initiative.setDescription(DEFAULT_DESCRIPTION);
        initiative.setStatus_id(DEFAULT_STATUS_ID);
        initiative.setCreated_by(DEFAULT_CREATED_BY);
        initiative.setCreated_date(DEFAULT_CREATED_DATE);
        initiative.setModified_by(DEFAULT_MODIFIED_BY);
        initiative.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createInitiative() throws Exception {
        int databaseSizeBeforeCreate = initiativeRepository.findAll().size();

        // Create the Initiative

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isCreated());

        // Validate the Initiative in the database
        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeCreate + 1);
        Initiative testInitiative = initiatives.get(initiatives.size() - 1);
        assertThat(testInitiative.getInitiative_name()).isEqualTo(DEFAULT_INITIATIVE_NAME);
        assertThat(testInitiative.getInitiative_type_id()).isEqualTo(DEFAULT_INITIATIVE_TYPE_ID);
        assertThat(testInitiative.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInitiative.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testInitiative.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInitiative.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInitiative.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testInitiative.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkInitiative_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setInitiative_name(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInitiative_type_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setInitiative_type_id(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setDescription(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setStatus_id(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setCreated_by(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setCreated_date(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setModified_by(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiativeRepository.findAll().size();
        // set the field null
        initiative.setModified_date(null);

        // Create the Initiative, which fails.

        restInitiativeMockMvc.perform(post("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isBadRequest());

        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInitiatives() throws Exception {
        // Initialize the database
        initiativeRepository.saveAndFlush(initiative);

        // Get all the initiatives
        restInitiativeMockMvc.perform(get("/api/initiatives"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(initiative.getId().intValue())))
                .andExpect(jsonPath("$.[*].initiative_name").value(hasItem(DEFAULT_INITIATIVE_NAME.toString())))
                .andExpect(jsonPath("$.[*].initiative_type_id").value(hasItem(DEFAULT_INITIATIVE_TYPE_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInitiative() throws Exception {
        // Initialize the database
        initiativeRepository.saveAndFlush(initiative);

        // Get the initiative
        restInitiativeMockMvc.perform(get("/api/initiatives/{id}", initiative.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(initiative.getId().intValue()))
            .andExpect(jsonPath("$.initiative_name").value(DEFAULT_INITIATIVE_NAME.toString()))
            .andExpect(jsonPath("$.initiative_type_id").value(DEFAULT_INITIATIVE_TYPE_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInitiative() throws Exception {
        // Get the initiative
        restInitiativeMockMvc.perform(get("/api/initiatives/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInitiative() throws Exception {
        // Initialize the database
        initiativeRepository.saveAndFlush(initiative);

		int databaseSizeBeforeUpdate = initiativeRepository.findAll().size();

        // Update the initiative
        initiative.setInitiative_name(UPDATED_INITIATIVE_NAME);
        initiative.setInitiative_type_id(UPDATED_INITIATIVE_TYPE_ID);
        initiative.setDescription(UPDATED_DESCRIPTION);
        initiative.setStatus_id(UPDATED_STATUS_ID);
        initiative.setCreated_by(UPDATED_CREATED_BY);
        initiative.setCreated_date(UPDATED_CREATED_DATE);
        initiative.setModified_by(UPDATED_MODIFIED_BY);
        initiative.setModified_date(UPDATED_MODIFIED_DATE);

        restInitiativeMockMvc.perform(put("/api/initiatives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative)))
                .andExpect(status().isOk());

        // Validate the Initiative in the database
        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeUpdate);
        Initiative testInitiative = initiatives.get(initiatives.size() - 1);
        assertThat(testInitiative.getInitiative_name()).isEqualTo(UPDATED_INITIATIVE_NAME);
        assertThat(testInitiative.getInitiative_type_id()).isEqualTo(UPDATED_INITIATIVE_TYPE_ID);
        assertThat(testInitiative.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInitiative.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testInitiative.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInitiative.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInitiative.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testInitiative.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteInitiative() throws Exception {
        // Initialize the database
        initiativeRepository.saveAndFlush(initiative);

		int databaseSizeBeforeDelete = initiativeRepository.findAll().size();

        // Get the initiative
        restInitiativeMockMvc.perform(delete("/api/initiatives/{id}", initiative.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Initiative> initiatives = initiativeRepository.findAll();
        assertThat(initiatives).hasSize(databaseSizeBeforeDelete - 1);
    }
}

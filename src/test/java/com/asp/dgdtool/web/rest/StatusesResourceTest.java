package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Statuses;
import com.asp.dgdtool.repository.StatusesRepository;
import com.asp.dgdtool.repository.search.StatusesSearchRepository;

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
 * Test class for the StatusesResource REST controller.
 *
 * @see StatusesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StatusesResourceTest {


    private static final Integer DEFAULT_STATUS_TYPE_ID = 1;
    private static final Integer UPDATED_STATUS_TYPE_ID = 2;
    private static final String DEFAULT_STATUS_NAME = "AAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBB";
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
    private StatusesRepository statusesRepository;

    @Inject
    private StatusesSearchRepository statusesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatusesMockMvc;

    private Statuses statuses;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StatusesResource statusesResource = new StatusesResource();
        ReflectionTestUtils.setField(statusesResource, "statusesRepository", statusesRepository);
        ReflectionTestUtils.setField(statusesResource, "statusesSearchRepository", statusesSearchRepository);
        this.restStatusesMockMvc = MockMvcBuilders.standaloneSetup(statusesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        statuses = new Statuses();
        statuses.setStatus_type_id(DEFAULT_STATUS_TYPE_ID);
        statuses.setStatus_name(DEFAULT_STATUS_NAME);
        statuses.setDescription(DEFAULT_DESCRIPTION);
        statuses.setStatus_id(DEFAULT_STATUS_ID);
        statuses.setCreated_by(DEFAULT_CREATED_BY);
        statuses.setCreated_date(DEFAULT_CREATED_DATE);
        statuses.setModified_by(DEFAULT_MODIFIED_BY);
        statuses.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createStatuses() throws Exception {
        int databaseSizeBeforeCreate = statusesRepository.findAll().size();

        // Create the Statuses

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isCreated());

        // Validate the Statuses in the database
        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeCreate + 1);
        Statuses testStatuses = statusess.get(statusess.size() - 1);
        assertThat(testStatuses.getStatus_type_id()).isEqualTo(DEFAULT_STATUS_TYPE_ID);
        assertThat(testStatuses.getStatus_name()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testStatuses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStatuses.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testStatuses.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStatuses.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStatuses.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testStatuses.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkStatus_type_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setStatus_type_id(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setStatus_name(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setDescription(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setStatus_id(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setCreated_by(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setCreated_date(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setModified_by(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusesRepository.findAll().size();
        // set the field null
        statuses.setModified_date(null);

        // Create the Statuses, which fails.

        restStatusesMockMvc.perform(post("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isBadRequest());

        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatusess() throws Exception {
        // Initialize the database
        statusesRepository.saveAndFlush(statuses);

        // Get all the statusess
        restStatusesMockMvc.perform(get("/api/statusess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(statuses.getId().intValue())))
                .andExpect(jsonPath("$.[*].status_type_id").value(hasItem(DEFAULT_STATUS_TYPE_ID)))
                .andExpect(jsonPath("$.[*].status_name").value(hasItem(DEFAULT_STATUS_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStatuses() throws Exception {
        // Initialize the database
        statusesRepository.saveAndFlush(statuses);

        // Get the statuses
        restStatusesMockMvc.perform(get("/api/statusess/{id}", statuses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(statuses.getId().intValue()))
            .andExpect(jsonPath("$.status_type_id").value(DEFAULT_STATUS_TYPE_ID))
            .andExpect(jsonPath("$.status_name").value(DEFAULT_STATUS_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStatuses() throws Exception {
        // Get the statuses
        restStatusesMockMvc.perform(get("/api/statusess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatuses() throws Exception {
        // Initialize the database
        statusesRepository.saveAndFlush(statuses);

		int databaseSizeBeforeUpdate = statusesRepository.findAll().size();

        // Update the statuses
        statuses.setStatus_type_id(UPDATED_STATUS_TYPE_ID);
        statuses.setStatus_name(UPDATED_STATUS_NAME);
        statuses.setDescription(UPDATED_DESCRIPTION);
        statuses.setStatus_id(UPDATED_STATUS_ID);
        statuses.setCreated_by(UPDATED_CREATED_BY);
        statuses.setCreated_date(UPDATED_CREATED_DATE);
        statuses.setModified_by(UPDATED_MODIFIED_BY);
        statuses.setModified_date(UPDATED_MODIFIED_DATE);

        restStatusesMockMvc.perform(put("/api/statusess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statuses)))
                .andExpect(status().isOk());

        // Validate the Statuses in the database
        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeUpdate);
        Statuses testStatuses = statusess.get(statusess.size() - 1);
        assertThat(testStatuses.getStatus_type_id()).isEqualTo(UPDATED_STATUS_TYPE_ID);
        assertThat(testStatuses.getStatus_name()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testStatuses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatuses.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testStatuses.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStatuses.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStatuses.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testStatuses.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteStatuses() throws Exception {
        // Initialize the database
        statusesRepository.saveAndFlush(statuses);

		int databaseSizeBeforeDelete = statusesRepository.findAll().size();

        // Get the statuses
        restStatusesMockMvc.perform(delete("/api/statusess/{id}", statuses.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Statuses> statusess = statusesRepository.findAll();
        assertThat(statusess).hasSize(databaseSizeBeforeDelete - 1);
    }
}

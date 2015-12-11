package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Data_state;
import com.asp.dgdtool.repository.Data_stateRepository;
import com.asp.dgdtool.repository.search.Data_stateSearchRepository;

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
 * Test class for the Data_stateResource REST controller.
 *
 * @see Data_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Data_stateResourceTest {

    private static final String DEFAULT_DATA_STATE_NAME = "AAAAA";
    private static final String UPDATED_DATA_STATE_NAME = "BBBBB";
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
    private Data_stateRepository data_stateRepository;

    @Inject
    private Data_stateSearchRepository data_stateSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restData_stateMockMvc;

    private Data_state data_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Data_stateResource data_stateResource = new Data_stateResource();
        ReflectionTestUtils.setField(data_stateResource, "data_stateRepository", data_stateRepository);
        ReflectionTestUtils.setField(data_stateResource, "data_stateSearchRepository", data_stateSearchRepository);
        this.restData_stateMockMvc = MockMvcBuilders.standaloneSetup(data_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        data_state = new Data_state();
        data_state.setData_state_name(DEFAULT_DATA_STATE_NAME);
        data_state.setDescription(DEFAULT_DESCRIPTION);
        data_state.setStatus_id(DEFAULT_STATUS_ID);
        data_state.setCreated_by(DEFAULT_CREATED_BY);
        data_state.setCreated_date(DEFAULT_CREATED_DATE);
        data_state.setModified_by(DEFAULT_MODIFIED_BY);
        data_state.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createData_state() throws Exception {
        int databaseSizeBeforeCreate = data_stateRepository.findAll().size();

        // Create the Data_state

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isCreated());

        // Validate the Data_state in the database
        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeCreate + 1);
        Data_state testData_state = data_states.get(data_states.size() - 1);
        assertThat(testData_state.getData_state_name()).isEqualTo(DEFAULT_DATA_STATE_NAME);
        assertThat(testData_state.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testData_state.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testData_state.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testData_state.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testData_state.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testData_state.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkData_state_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_stateRepository.findAll().size();
        // set the field null
        data_state.setData_state_name(null);

        // Create the Data_state, which fails.

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isBadRequest());

        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_stateRepository.findAll().size();
        // set the field null
        data_state.setDescription(null);

        // Create the Data_state, which fails.

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isBadRequest());

        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_stateRepository.findAll().size();
        // set the field null
        data_state.setStatus_id(null);

        // Create the Data_state, which fails.

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isBadRequest());

        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_stateRepository.findAll().size();
        // set the field null
        data_state.setCreated_by(null);

        // Create the Data_state, which fails.

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isBadRequest());

        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_stateRepository.findAll().size();
        // set the field null
        data_state.setCreated_date(null);

        // Create the Data_state, which fails.

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isBadRequest());

        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_stateRepository.findAll().size();
        // set the field null
        data_state.setModified_by(null);

        // Create the Data_state, which fails.

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isBadRequest());

        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_stateRepository.findAll().size();
        // set the field null
        data_state.setModified_date(null);

        // Create the Data_state, which fails.

        restData_stateMockMvc.perform(post("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isBadRequest());

        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllData_states() throws Exception {
        // Initialize the database
        data_stateRepository.saveAndFlush(data_state);

        // Get all the data_states
        restData_stateMockMvc.perform(get("/api/data_states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(data_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].data_state_name").value(hasItem(DEFAULT_DATA_STATE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getData_state() throws Exception {
        // Initialize the database
        data_stateRepository.saveAndFlush(data_state);

        // Get the data_state
        restData_stateMockMvc.perform(get("/api/data_states/{id}", data_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(data_state.getId().intValue()))
            .andExpect(jsonPath("$.data_state_name").value(DEFAULT_DATA_STATE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingData_state() throws Exception {
        // Get the data_state
        restData_stateMockMvc.perform(get("/api/data_states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateData_state() throws Exception {
        // Initialize the database
        data_stateRepository.saveAndFlush(data_state);

		int databaseSizeBeforeUpdate = data_stateRepository.findAll().size();

        // Update the data_state
        data_state.setData_state_name(UPDATED_DATA_STATE_NAME);
        data_state.setDescription(UPDATED_DESCRIPTION);
        data_state.setStatus_id(UPDATED_STATUS_ID);
        data_state.setCreated_by(UPDATED_CREATED_BY);
        data_state.setCreated_date(UPDATED_CREATED_DATE);
        data_state.setModified_by(UPDATED_MODIFIED_BY);
        data_state.setModified_date(UPDATED_MODIFIED_DATE);

        restData_stateMockMvc.perform(put("/api/data_states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_state)))
                .andExpect(status().isOk());

        // Validate the Data_state in the database
        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeUpdate);
        Data_state testData_state = data_states.get(data_states.size() - 1);
        assertThat(testData_state.getData_state_name()).isEqualTo(UPDATED_DATA_STATE_NAME);
        assertThat(testData_state.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testData_state.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testData_state.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testData_state.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testData_state.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testData_state.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteData_state() throws Exception {
        // Initialize the database
        data_stateRepository.saveAndFlush(data_state);

		int databaseSizeBeforeDelete = data_stateRepository.findAll().size();

        // Get the data_state
        restData_stateMockMvc.perform(delete("/api/data_states/{id}", data_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Data_state> data_states = data_stateRepository.findAll();
        assertThat(data_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}

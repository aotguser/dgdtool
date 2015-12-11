package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Status_type;
import com.asp.dgdtool.repository.Status_typeRepository;
import com.asp.dgdtool.repository.search.Status_typeSearchRepository;

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
 * Test class for the Status_typeResource REST controller.
 *
 * @see Status_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Status_typeResourceTest {

    private static final String DEFAULT_STATUS_TYPE_NAME = "AAAAA";
    private static final String UPDATED_STATUS_TYPE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_MODIFIED_BY = "AAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private Status_typeRepository status_typeRepository;

    @Inject
    private Status_typeSearchRepository status_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatus_typeMockMvc;

    private Status_type status_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Status_typeResource status_typeResource = new Status_typeResource();
        ReflectionTestUtils.setField(status_typeResource, "status_typeRepository", status_typeRepository);
        ReflectionTestUtils.setField(status_typeResource, "status_typeSearchRepository", status_typeSearchRepository);
        this.restStatus_typeMockMvc = MockMvcBuilders.standaloneSetup(status_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        status_type = new Status_type();
        status_type.setStatus_type_name(DEFAULT_STATUS_TYPE_NAME);
        status_type.setDescription(DEFAULT_DESCRIPTION);
        status_type.setCreated_by(DEFAULT_CREATED_BY);
        status_type.setCreated_date(DEFAULT_CREATED_DATE);
        status_type.setModified_by(DEFAULT_MODIFIED_BY);
        status_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createStatus_type() throws Exception {
        int databaseSizeBeforeCreate = status_typeRepository.findAll().size();

        // Create the Status_type

        restStatus_typeMockMvc.perform(post("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isCreated());

        // Validate the Status_type in the database
        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeCreate + 1);
        Status_type testStatus_type = status_types.get(status_types.size() - 1);
        assertThat(testStatus_type.getStatus_type_name()).isEqualTo(DEFAULT_STATUS_TYPE_NAME);
        assertThat(testStatus_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStatus_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStatus_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStatus_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testStatus_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkStatus_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_typeRepository.findAll().size();
        // set the field null
        status_type.setStatus_type_name(null);

        // Create the Status_type, which fails.

        restStatus_typeMockMvc.perform(post("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isBadRequest());

        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_typeRepository.findAll().size();
        // set the field null
        status_type.setDescription(null);

        // Create the Status_type, which fails.

        restStatus_typeMockMvc.perform(post("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isBadRequest());

        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_typeRepository.findAll().size();
        // set the field null
        status_type.setCreated_by(null);

        // Create the Status_type, which fails.

        restStatus_typeMockMvc.perform(post("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isBadRequest());

        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_typeRepository.findAll().size();
        // set the field null
        status_type.setCreated_date(null);

        // Create the Status_type, which fails.

        restStatus_typeMockMvc.perform(post("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isBadRequest());

        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_typeRepository.findAll().size();
        // set the field null
        status_type.setModified_by(null);

        // Create the Status_type, which fails.

        restStatus_typeMockMvc.perform(post("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isBadRequest());

        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = status_typeRepository.findAll().size();
        // set the field null
        status_type.setModified_date(null);

        // Create the Status_type, which fails.

        restStatus_typeMockMvc.perform(post("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isBadRequest());

        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatus_types() throws Exception {
        // Initialize the database
        status_typeRepository.saveAndFlush(status_type);

        // Get all the status_types
        restStatus_typeMockMvc.perform(get("/api/status_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].status_type_name").value(hasItem(DEFAULT_STATUS_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStatus_type() throws Exception {
        // Initialize the database
        status_typeRepository.saveAndFlush(status_type);

        // Get the status_type
        restStatus_typeMockMvc.perform(get("/api/status_types/{id}", status_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status_type.getId().intValue()))
            .andExpect(jsonPath("$.status_type_name").value(DEFAULT_STATUS_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStatus_type() throws Exception {
        // Get the status_type
        restStatus_typeMockMvc.perform(get("/api/status_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus_type() throws Exception {
        // Initialize the database
        status_typeRepository.saveAndFlush(status_type);

		int databaseSizeBeforeUpdate = status_typeRepository.findAll().size();

        // Update the status_type
        status_type.setStatus_type_name(UPDATED_STATUS_TYPE_NAME);
        status_type.setDescription(UPDATED_DESCRIPTION);
        status_type.setCreated_by(UPDATED_CREATED_BY);
        status_type.setCreated_date(UPDATED_CREATED_DATE);
        status_type.setModified_by(UPDATED_MODIFIED_BY);
        status_type.setModified_date(UPDATED_MODIFIED_DATE);

        restStatus_typeMockMvc.perform(put("/api/status_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(status_type)))
                .andExpect(status().isOk());

        // Validate the Status_type in the database
        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeUpdate);
        Status_type testStatus_type = status_types.get(status_types.size() - 1);
        assertThat(testStatus_type.getStatus_type_name()).isEqualTo(UPDATED_STATUS_TYPE_NAME);
        assertThat(testStatus_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatus_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStatus_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStatus_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testStatus_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteStatus_type() throws Exception {
        // Initialize the database
        status_typeRepository.saveAndFlush(status_type);

		int databaseSizeBeforeDelete = status_typeRepository.findAll().size();

        // Get the status_type
        restStatus_typeMockMvc.perform(delete("/api/status_types/{id}", status_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status_type> status_types = status_typeRepository.findAll();
        assertThat(status_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

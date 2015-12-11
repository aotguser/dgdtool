package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Data_type;
import com.asp.dgdtool.repository.Data_typeRepository;
import com.asp.dgdtool.repository.search.Data_typeSearchRepository;

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
 * Test class for the Data_typeResource REST controller.
 *
 * @see Data_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Data_typeResourceTest {

    private static final String DEFAULT_DATA_TYPE_NAME = "AAAAA";
    private static final String UPDATED_DATA_TYPE_NAME = "BBBBB";
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
    private Data_typeRepository data_typeRepository;

    @Inject
    private Data_typeSearchRepository data_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restData_typeMockMvc;

    private Data_type data_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Data_typeResource data_typeResource = new Data_typeResource();
        ReflectionTestUtils.setField(data_typeResource, "data_typeRepository", data_typeRepository);
        ReflectionTestUtils.setField(data_typeResource, "data_typeSearchRepository", data_typeSearchRepository);
        this.restData_typeMockMvc = MockMvcBuilders.standaloneSetup(data_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        data_type = new Data_type();
        data_type.setData_type_name(DEFAULT_DATA_TYPE_NAME);
        data_type.setDescription(DEFAULT_DESCRIPTION);
        data_type.setStatus_id(DEFAULT_STATUS_ID);
        data_type.setCreated_by(DEFAULT_CREATED_BY);
        data_type.setCreated_date(DEFAULT_CREATED_DATE);
        data_type.setModified_by(DEFAULT_MODIFIED_BY);
        data_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createData_type() throws Exception {
        int databaseSizeBeforeCreate = data_typeRepository.findAll().size();

        // Create the Data_type

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isCreated());

        // Validate the Data_type in the database
        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeCreate + 1);
        Data_type testData_type = data_types.get(data_types.size() - 1);
        assertThat(testData_type.getData_type_name()).isEqualTo(DEFAULT_DATA_TYPE_NAME);
        assertThat(testData_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testData_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testData_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testData_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testData_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testData_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkData_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_typeRepository.findAll().size();
        // set the field null
        data_type.setData_type_name(null);

        // Create the Data_type, which fails.

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isBadRequest());

        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_typeRepository.findAll().size();
        // set the field null
        data_type.setDescription(null);

        // Create the Data_type, which fails.

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isBadRequest());

        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_typeRepository.findAll().size();
        // set the field null
        data_type.setStatus_id(null);

        // Create the Data_type, which fails.

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isBadRequest());

        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_typeRepository.findAll().size();
        // set the field null
        data_type.setCreated_by(null);

        // Create the Data_type, which fails.

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isBadRequest());

        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_typeRepository.findAll().size();
        // set the field null
        data_type.setCreated_date(null);

        // Create the Data_type, which fails.

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isBadRequest());

        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_typeRepository.findAll().size();
        // set the field null
        data_type.setModified_by(null);

        // Create the Data_type, which fails.

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isBadRequest());

        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = data_typeRepository.findAll().size();
        // set the field null
        data_type.setModified_date(null);

        // Create the Data_type, which fails.

        restData_typeMockMvc.perform(post("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isBadRequest());

        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllData_types() throws Exception {
        // Initialize the database
        data_typeRepository.saveAndFlush(data_type);

        // Get all the data_types
        restData_typeMockMvc.perform(get("/api/data_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(data_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].data_type_name").value(hasItem(DEFAULT_DATA_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getData_type() throws Exception {
        // Initialize the database
        data_typeRepository.saveAndFlush(data_type);

        // Get the data_type
        restData_typeMockMvc.perform(get("/api/data_types/{id}", data_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(data_type.getId().intValue()))
            .andExpect(jsonPath("$.data_type_name").value(DEFAULT_DATA_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingData_type() throws Exception {
        // Get the data_type
        restData_typeMockMvc.perform(get("/api/data_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateData_type() throws Exception {
        // Initialize the database
        data_typeRepository.saveAndFlush(data_type);

		int databaseSizeBeforeUpdate = data_typeRepository.findAll().size();

        // Update the data_type
        data_type.setData_type_name(UPDATED_DATA_TYPE_NAME);
        data_type.setDescription(UPDATED_DESCRIPTION);
        data_type.setStatus_id(UPDATED_STATUS_ID);
        data_type.setCreated_by(UPDATED_CREATED_BY);
        data_type.setCreated_date(UPDATED_CREATED_DATE);
        data_type.setModified_by(UPDATED_MODIFIED_BY);
        data_type.setModified_date(UPDATED_MODIFIED_DATE);

        restData_typeMockMvc.perform(put("/api/data_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(data_type)))
                .andExpect(status().isOk());

        // Validate the Data_type in the database
        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeUpdate);
        Data_type testData_type = data_types.get(data_types.size() - 1);
        assertThat(testData_type.getData_type_name()).isEqualTo(UPDATED_DATA_TYPE_NAME);
        assertThat(testData_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testData_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testData_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testData_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testData_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testData_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteData_type() throws Exception {
        // Initialize the database
        data_typeRepository.saveAndFlush(data_type);

		int databaseSizeBeforeDelete = data_typeRepository.findAll().size();

        // Get the data_type
        restData_typeMockMvc.perform(delete("/api/data_types/{id}", data_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Data_type> data_types = data_typeRepository.findAll();
        assertThat(data_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

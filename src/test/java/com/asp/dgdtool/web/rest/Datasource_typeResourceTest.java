package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Datasource_type;
import com.asp.dgdtool.repository.Datasource_typeRepository;
import com.asp.dgdtool.repository.search.Datasource_typeSearchRepository;

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
 * Test class for the Datasource_typeResource REST controller.
 *
 * @see Datasource_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Datasource_typeResourceTest {

    private static final String DEFAULT_DATASOURCE_TYPE_NAME = "AAAAA";
    private static final String UPDATED_DATASOURCE_TYPE_NAME = "BBBBB";
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
    private Datasource_typeRepository datasource_typeRepository;

    @Inject
    private Datasource_typeSearchRepository datasource_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDatasource_typeMockMvc;

    private Datasource_type datasource_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Datasource_typeResource datasource_typeResource = new Datasource_typeResource();
        ReflectionTestUtils.setField(datasource_typeResource, "datasource_typeRepository", datasource_typeRepository);
        ReflectionTestUtils.setField(datasource_typeResource, "datasource_typeSearchRepository", datasource_typeSearchRepository);
        this.restDatasource_typeMockMvc = MockMvcBuilders.standaloneSetup(datasource_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        datasource_type = new Datasource_type();
        datasource_type.setDatasource_type_name(DEFAULT_DATASOURCE_TYPE_NAME);
        datasource_type.setDescription(DEFAULT_DESCRIPTION);
        datasource_type.setStatus_id(DEFAULT_STATUS_ID);
        datasource_type.setCreated_by(DEFAULT_CREATED_BY);
        datasource_type.setCreated_date(DEFAULT_CREATED_DATE);
        datasource_type.setModified_by(DEFAULT_MODIFIED_BY);
        datasource_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDatasource_type() throws Exception {
        int databaseSizeBeforeCreate = datasource_typeRepository.findAll().size();

        // Create the Datasource_type

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isCreated());

        // Validate the Datasource_type in the database
        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeCreate + 1);
        Datasource_type testDatasource_type = datasource_types.get(datasource_types.size() - 1);
        assertThat(testDatasource_type.getDatasource_type_name()).isEqualTo(DEFAULT_DATASOURCE_TYPE_NAME);
        assertThat(testDatasource_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDatasource_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testDatasource_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDatasource_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDatasource_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDatasource_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkDatasource_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasource_typeRepository.findAll().size();
        // set the field null
        datasource_type.setDatasource_type_name(null);

        // Create the Datasource_type, which fails.

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isBadRequest());

        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasource_typeRepository.findAll().size();
        // set the field null
        datasource_type.setDescription(null);

        // Create the Datasource_type, which fails.

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isBadRequest());

        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasource_typeRepository.findAll().size();
        // set the field null
        datasource_type.setStatus_id(null);

        // Create the Datasource_type, which fails.

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isBadRequest());

        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasource_typeRepository.findAll().size();
        // set the field null
        datasource_type.setCreated_by(null);

        // Create the Datasource_type, which fails.

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isBadRequest());

        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasource_typeRepository.findAll().size();
        // set the field null
        datasource_type.setCreated_date(null);

        // Create the Datasource_type, which fails.

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isBadRequest());

        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasource_typeRepository.findAll().size();
        // set the field null
        datasource_type.setModified_by(null);

        // Create the Datasource_type, which fails.

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isBadRequest());

        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasource_typeRepository.findAll().size();
        // set the field null
        datasource_type.setModified_date(null);

        // Create the Datasource_type, which fails.

        restDatasource_typeMockMvc.perform(post("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isBadRequest());

        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasource_types() throws Exception {
        // Initialize the database
        datasource_typeRepository.saveAndFlush(datasource_type);

        // Get all the datasource_types
        restDatasource_typeMockMvc.perform(get("/api/datasource_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(datasource_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].datasource_type_name").value(hasItem(DEFAULT_DATASOURCE_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDatasource_type() throws Exception {
        // Initialize the database
        datasource_typeRepository.saveAndFlush(datasource_type);

        // Get the datasource_type
        restDatasource_typeMockMvc.perform(get("/api/datasource_types/{id}", datasource_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(datasource_type.getId().intValue()))
            .andExpect(jsonPath("$.datasource_type_name").value(DEFAULT_DATASOURCE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasource_type() throws Exception {
        // Get the datasource_type
        restDatasource_typeMockMvc.perform(get("/api/datasource_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasource_type() throws Exception {
        // Initialize the database
        datasource_typeRepository.saveAndFlush(datasource_type);

		int databaseSizeBeforeUpdate = datasource_typeRepository.findAll().size();

        // Update the datasource_type
        datasource_type.setDatasource_type_name(UPDATED_DATASOURCE_TYPE_NAME);
        datasource_type.setDescription(UPDATED_DESCRIPTION);
        datasource_type.setStatus_id(UPDATED_STATUS_ID);
        datasource_type.setCreated_by(UPDATED_CREATED_BY);
        datasource_type.setCreated_date(UPDATED_CREATED_DATE);
        datasource_type.setModified_by(UPDATED_MODIFIED_BY);
        datasource_type.setModified_date(UPDATED_MODIFIED_DATE);

        restDatasource_typeMockMvc.perform(put("/api/datasource_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource_type)))
                .andExpect(status().isOk());

        // Validate the Datasource_type in the database
        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeUpdate);
        Datasource_type testDatasource_type = datasource_types.get(datasource_types.size() - 1);
        assertThat(testDatasource_type.getDatasource_type_name()).isEqualTo(UPDATED_DATASOURCE_TYPE_NAME);
        assertThat(testDatasource_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDatasource_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testDatasource_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDatasource_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDatasource_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDatasource_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteDatasource_type() throws Exception {
        // Initialize the database
        datasource_typeRepository.saveAndFlush(datasource_type);

		int databaseSizeBeforeDelete = datasource_typeRepository.findAll().size();

        // Get the datasource_type
        restDatasource_typeMockMvc.perform(delete("/api/datasource_types/{id}", datasource_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Datasource_type> datasource_types = datasource_typeRepository.findAll();
        assertThat(datasource_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

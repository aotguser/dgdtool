package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Field_type;
import com.asp.dgdtool.repository.Field_typeRepository;
import com.asp.dgdtool.repository.search.Field_typeSearchRepository;

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
 * Test class for the Field_typeResource REST controller.
 *
 * @see Field_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Field_typeResourceTest {

    private static final String DEFAULT_FIELD_TYPE_NAME = "AAAAA";
    private static final String UPDATED_FIELD_TYPE_NAME = "BBBBB";
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
    private Field_typeRepository field_typeRepository;

    @Inject
    private Field_typeSearchRepository field_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restField_typeMockMvc;

    private Field_type field_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Field_typeResource field_typeResource = new Field_typeResource();
        ReflectionTestUtils.setField(field_typeResource, "field_typeRepository", field_typeRepository);
        ReflectionTestUtils.setField(field_typeResource, "field_typeSearchRepository", field_typeSearchRepository);
        this.restField_typeMockMvc = MockMvcBuilders.standaloneSetup(field_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        field_type = new Field_type();
        field_type.setField_type_name(DEFAULT_FIELD_TYPE_NAME);
        field_type.setDescription(DEFAULT_DESCRIPTION);
        field_type.setStatus_id(DEFAULT_STATUS_ID);
        field_type.setCreated_by(DEFAULT_CREATED_BY);
        field_type.setCreated_date(DEFAULT_CREATED_DATE);
        field_type.setModified_by(DEFAULT_MODIFIED_BY);
        field_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createField_type() throws Exception {
        int databaseSizeBeforeCreate = field_typeRepository.findAll().size();

        // Create the Field_type

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isCreated());

        // Validate the Field_type in the database
        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeCreate + 1);
        Field_type testField_type = field_types.get(field_types.size() - 1);
        assertThat(testField_type.getField_type_name()).isEqualTo(DEFAULT_FIELD_TYPE_NAME);
        assertThat(testField_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testField_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testField_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testField_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testField_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testField_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkField_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = field_typeRepository.findAll().size();
        // set the field null
        field_type.setField_type_name(null);

        // Create the Field_type, which fails.

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isBadRequest());

        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = field_typeRepository.findAll().size();
        // set the field null
        field_type.setDescription(null);

        // Create the Field_type, which fails.

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isBadRequest());

        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = field_typeRepository.findAll().size();
        // set the field null
        field_type.setStatus_id(null);

        // Create the Field_type, which fails.

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isBadRequest());

        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = field_typeRepository.findAll().size();
        // set the field null
        field_type.setCreated_by(null);

        // Create the Field_type, which fails.

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isBadRequest());

        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = field_typeRepository.findAll().size();
        // set the field null
        field_type.setCreated_date(null);

        // Create the Field_type, which fails.

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isBadRequest());

        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = field_typeRepository.findAll().size();
        // set the field null
        field_type.setModified_by(null);

        // Create the Field_type, which fails.

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isBadRequest());

        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = field_typeRepository.findAll().size();
        // set the field null
        field_type.setModified_date(null);

        // Create the Field_type, which fails.

        restField_typeMockMvc.perform(post("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isBadRequest());

        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllField_types() throws Exception {
        // Initialize the database
        field_typeRepository.saveAndFlush(field_type);

        // Get all the field_types
        restField_typeMockMvc.perform(get("/api/field_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(field_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].field_type_name").value(hasItem(DEFAULT_FIELD_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getField_type() throws Exception {
        // Initialize the database
        field_typeRepository.saveAndFlush(field_type);

        // Get the field_type
        restField_typeMockMvc.perform(get("/api/field_types/{id}", field_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(field_type.getId().intValue()))
            .andExpect(jsonPath("$.field_type_name").value(DEFAULT_FIELD_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingField_type() throws Exception {
        // Get the field_type
        restField_typeMockMvc.perform(get("/api/field_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateField_type() throws Exception {
        // Initialize the database
        field_typeRepository.saveAndFlush(field_type);

		int databaseSizeBeforeUpdate = field_typeRepository.findAll().size();

        // Update the field_type
        field_type.setField_type_name(UPDATED_FIELD_TYPE_NAME);
        field_type.setDescription(UPDATED_DESCRIPTION);
        field_type.setStatus_id(UPDATED_STATUS_ID);
        field_type.setCreated_by(UPDATED_CREATED_BY);
        field_type.setCreated_date(UPDATED_CREATED_DATE);
        field_type.setModified_by(UPDATED_MODIFIED_BY);
        field_type.setModified_date(UPDATED_MODIFIED_DATE);

        restField_typeMockMvc.perform(put("/api/field_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(field_type)))
                .andExpect(status().isOk());

        // Validate the Field_type in the database
        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeUpdate);
        Field_type testField_type = field_types.get(field_types.size() - 1);
        assertThat(testField_type.getField_type_name()).isEqualTo(UPDATED_FIELD_TYPE_NAME);
        assertThat(testField_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testField_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testField_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testField_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testField_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testField_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteField_type() throws Exception {
        // Initialize the database
        field_typeRepository.saveAndFlush(field_type);

		int databaseSizeBeforeDelete = field_typeRepository.findAll().size();

        // Get the field_type
        restField_typeMockMvc.perform(delete("/api/field_types/{id}", field_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Field_type> field_types = field_typeRepository.findAll();
        assertThat(field_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

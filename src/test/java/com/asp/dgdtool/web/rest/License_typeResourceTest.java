package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.License_type;
import com.asp.dgdtool.repository.License_typeRepository;
import com.asp.dgdtool.repository.search.License_typeSearchRepository;

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
 * Test class for the License_typeResource REST controller.
 *
 * @see License_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class License_typeResourceTest {

    private static final String DEFAULT_LICENSE_TYPE_NAME = "AAAAA";
    private static final String UPDATED_LICENSE_TYPE_NAME = "BBBBB";
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
    private License_typeRepository license_typeRepository;

    @Inject
    private License_typeSearchRepository license_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLicense_typeMockMvc;

    private License_type license_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        License_typeResource license_typeResource = new License_typeResource();
        ReflectionTestUtils.setField(license_typeResource, "license_typeRepository", license_typeRepository);
        ReflectionTestUtils.setField(license_typeResource, "license_typeSearchRepository", license_typeSearchRepository);
        this.restLicense_typeMockMvc = MockMvcBuilders.standaloneSetup(license_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        license_type = new License_type();
        license_type.setLicense_type_name(DEFAULT_LICENSE_TYPE_NAME);
        license_type.setDescription(DEFAULT_DESCRIPTION);
        license_type.setStatus_id(DEFAULT_STATUS_ID);
        license_type.setCreated_by(DEFAULT_CREATED_BY);
        license_type.setCreated_date(DEFAULT_CREATED_DATE);
        license_type.setModified_by(DEFAULT_MODIFIED_BY);
        license_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createLicense_type() throws Exception {
        int databaseSizeBeforeCreate = license_typeRepository.findAll().size();

        // Create the License_type

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isCreated());

        // Validate the License_type in the database
        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeCreate + 1);
        License_type testLicense_type = license_types.get(license_types.size() - 1);
        assertThat(testLicense_type.getLicense_type_name()).isEqualTo(DEFAULT_LICENSE_TYPE_NAME);
        assertThat(testLicense_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLicense_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testLicense_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLicense_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLicense_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testLicense_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkLicense_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = license_typeRepository.findAll().size();
        // set the field null
        license_type.setLicense_type_name(null);

        // Create the License_type, which fails.

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isBadRequest());

        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = license_typeRepository.findAll().size();
        // set the field null
        license_type.setDescription(null);

        // Create the License_type, which fails.

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isBadRequest());

        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = license_typeRepository.findAll().size();
        // set the field null
        license_type.setStatus_id(null);

        // Create the License_type, which fails.

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isBadRequest());

        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = license_typeRepository.findAll().size();
        // set the field null
        license_type.setCreated_by(null);

        // Create the License_type, which fails.

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isBadRequest());

        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = license_typeRepository.findAll().size();
        // set the field null
        license_type.setCreated_date(null);

        // Create the License_type, which fails.

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isBadRequest());

        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = license_typeRepository.findAll().size();
        // set the field null
        license_type.setModified_by(null);

        // Create the License_type, which fails.

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isBadRequest());

        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = license_typeRepository.findAll().size();
        // set the field null
        license_type.setModified_date(null);

        // Create the License_type, which fails.

        restLicense_typeMockMvc.perform(post("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isBadRequest());

        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLicense_types() throws Exception {
        // Initialize the database
        license_typeRepository.saveAndFlush(license_type);

        // Get all the license_types
        restLicense_typeMockMvc.perform(get("/api/license_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(license_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].license_type_name").value(hasItem(DEFAULT_LICENSE_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getLicense_type() throws Exception {
        // Initialize the database
        license_typeRepository.saveAndFlush(license_type);

        // Get the license_type
        restLicense_typeMockMvc.perform(get("/api/license_types/{id}", license_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(license_type.getId().intValue()))
            .andExpect(jsonPath("$.license_type_name").value(DEFAULT_LICENSE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLicense_type() throws Exception {
        // Get the license_type
        restLicense_typeMockMvc.perform(get("/api/license_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicense_type() throws Exception {
        // Initialize the database
        license_typeRepository.saveAndFlush(license_type);

		int databaseSizeBeforeUpdate = license_typeRepository.findAll().size();

        // Update the license_type
        license_type.setLicense_type_name(UPDATED_LICENSE_TYPE_NAME);
        license_type.setDescription(UPDATED_DESCRIPTION);
        license_type.setStatus_id(UPDATED_STATUS_ID);
        license_type.setCreated_by(UPDATED_CREATED_BY);
        license_type.setCreated_date(UPDATED_CREATED_DATE);
        license_type.setModified_by(UPDATED_MODIFIED_BY);
        license_type.setModified_date(UPDATED_MODIFIED_DATE);

        restLicense_typeMockMvc.perform(put("/api/license_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(license_type)))
                .andExpect(status().isOk());

        // Validate the License_type in the database
        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeUpdate);
        License_type testLicense_type = license_types.get(license_types.size() - 1);
        assertThat(testLicense_type.getLicense_type_name()).isEqualTo(UPDATED_LICENSE_TYPE_NAME);
        assertThat(testLicense_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLicense_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testLicense_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLicense_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLicense_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testLicense_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteLicense_type() throws Exception {
        // Initialize the database
        license_typeRepository.saveAndFlush(license_type);

		int databaseSizeBeforeDelete = license_typeRepository.findAll().size();

        // Get the license_type
        restLicense_typeMockMvc.perform(delete("/api/license_types/{id}", license_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<License_type> license_types = license_typeRepository.findAll();
        assertThat(license_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

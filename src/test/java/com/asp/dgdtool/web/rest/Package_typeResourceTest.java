package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Package_type;
import com.asp.dgdtool.repository.Package_typeRepository;
import com.asp.dgdtool.repository.search.Package_typeSearchRepository;

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
 * Test class for the Package_typeResource REST controller.
 *
 * @see Package_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Package_typeResourceTest {

    private static final String DEFAULT_PACKAGE_TYPE_NAME = "AAAAA";
    private static final String UPDATED_PACKAGE_TYPE_NAME = "BBBBB";
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
    private Package_typeRepository package_typeRepository;

    @Inject
    private Package_typeSearchRepository package_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPackage_typeMockMvc;

    private Package_type package_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Package_typeResource package_typeResource = new Package_typeResource();
        ReflectionTestUtils.setField(package_typeResource, "package_typeRepository", package_typeRepository);
        ReflectionTestUtils.setField(package_typeResource, "package_typeSearchRepository", package_typeSearchRepository);
        this.restPackage_typeMockMvc = MockMvcBuilders.standaloneSetup(package_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        package_type = new Package_type();
        package_type.setPackage_type_name(DEFAULT_PACKAGE_TYPE_NAME);
        package_type.setDescription(DEFAULT_DESCRIPTION);
        package_type.setStatus_id(DEFAULT_STATUS_ID);
        package_type.setCreated_by(DEFAULT_CREATED_BY);
        package_type.setCreated_date(DEFAULT_CREATED_DATE);
        package_type.setModified_by(DEFAULT_MODIFIED_BY);
        package_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPackage_type() throws Exception {
        int databaseSizeBeforeCreate = package_typeRepository.findAll().size();

        // Create the Package_type

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isCreated());

        // Validate the Package_type in the database
        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeCreate + 1);
        Package_type testPackage_type = package_types.get(package_types.size() - 1);
        assertThat(testPackage_type.getPackage_type_name()).isEqualTo(DEFAULT_PACKAGE_TYPE_NAME);
        assertThat(testPackage_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPackage_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testPackage_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPackage_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackage_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPackage_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkPackage_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_typeRepository.findAll().size();
        // set the field null
        package_type.setPackage_type_name(null);

        // Create the Package_type, which fails.

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isBadRequest());

        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_typeRepository.findAll().size();
        // set the field null
        package_type.setDescription(null);

        // Create the Package_type, which fails.

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isBadRequest());

        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_typeRepository.findAll().size();
        // set the field null
        package_type.setStatus_id(null);

        // Create the Package_type, which fails.

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isBadRequest());

        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_typeRepository.findAll().size();
        // set the field null
        package_type.setCreated_by(null);

        // Create the Package_type, which fails.

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isBadRequest());

        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_typeRepository.findAll().size();
        // set the field null
        package_type.setCreated_date(null);

        // Create the Package_type, which fails.

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isBadRequest());

        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_typeRepository.findAll().size();
        // set the field null
        package_type.setModified_by(null);

        // Create the Package_type, which fails.

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isBadRequest());

        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_typeRepository.findAll().size();
        // set the field null
        package_type.setModified_date(null);

        // Create the Package_type, which fails.

        restPackage_typeMockMvc.perform(post("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isBadRequest());

        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackage_types() throws Exception {
        // Initialize the database
        package_typeRepository.saveAndFlush(package_type);

        // Get all the package_types
        restPackage_typeMockMvc.perform(get("/api/package_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(package_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].package_type_name").value(hasItem(DEFAULT_PACKAGE_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPackage_type() throws Exception {
        // Initialize the database
        package_typeRepository.saveAndFlush(package_type);

        // Get the package_type
        restPackage_typeMockMvc.perform(get("/api/package_types/{id}", package_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(package_type.getId().intValue()))
            .andExpect(jsonPath("$.package_type_name").value(DEFAULT_PACKAGE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackage_type() throws Exception {
        // Get the package_type
        restPackage_typeMockMvc.perform(get("/api/package_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackage_type() throws Exception {
        // Initialize the database
        package_typeRepository.saveAndFlush(package_type);

		int databaseSizeBeforeUpdate = package_typeRepository.findAll().size();

        // Update the package_type
        package_type.setPackage_type_name(UPDATED_PACKAGE_TYPE_NAME);
        package_type.setDescription(UPDATED_DESCRIPTION);
        package_type.setStatus_id(UPDATED_STATUS_ID);
        package_type.setCreated_by(UPDATED_CREATED_BY);
        package_type.setCreated_date(UPDATED_CREATED_DATE);
        package_type.setModified_by(UPDATED_MODIFIED_BY);
        package_type.setModified_date(UPDATED_MODIFIED_DATE);

        restPackage_typeMockMvc.perform(put("/api/package_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_type)))
                .andExpect(status().isOk());

        // Validate the Package_type in the database
        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeUpdate);
        Package_type testPackage_type = package_types.get(package_types.size() - 1);
        assertThat(testPackage_type.getPackage_type_name()).isEqualTo(UPDATED_PACKAGE_TYPE_NAME);
        assertThat(testPackage_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPackage_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testPackage_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackage_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackage_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPackage_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePackage_type() throws Exception {
        // Initialize the database
        package_typeRepository.saveAndFlush(package_type);

		int databaseSizeBeforeDelete = package_typeRepository.findAll().size();

        // Get the package_type
        restPackage_typeMockMvc.perform(delete("/api/package_types/{id}", package_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Package_type> package_types = package_typeRepository.findAll();
        assertThat(package_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

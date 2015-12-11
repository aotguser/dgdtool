package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Package_version;
import com.asp.dgdtool.repository.Package_versionRepository;
import com.asp.dgdtool.repository.search.Package_versionSearchRepository;

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
 * Test class for the Package_versionResource REST controller.
 *
 * @see Package_versionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Package_versionResourceTest {

    private static final String DEFAULT_PACKAGE_VERSION_NAME = "AAAAA";
    private static final String UPDATED_PACKAGE_VERSION_NAME = "BBBBB";

    private static final Integer DEFAULT_PACKAGE_ID = 1;
    private static final Integer UPDATED_PACKAGE_ID = 2;
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
    private Package_versionRepository package_versionRepository;

    @Inject
    private Package_versionSearchRepository package_versionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPackage_versionMockMvc;

    private Package_version package_version;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Package_versionResource package_versionResource = new Package_versionResource();
        ReflectionTestUtils.setField(package_versionResource, "package_versionRepository", package_versionRepository);
        ReflectionTestUtils.setField(package_versionResource, "package_versionSearchRepository", package_versionSearchRepository);
        this.restPackage_versionMockMvc = MockMvcBuilders.standaloneSetup(package_versionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        package_version = new Package_version();
        package_version.setPackage_version_name(DEFAULT_PACKAGE_VERSION_NAME);
        package_version.setPackage_id(DEFAULT_PACKAGE_ID);
        package_version.setDescription(DEFAULT_DESCRIPTION);
        package_version.setStatus_id(DEFAULT_STATUS_ID);
        package_version.setCreated_by(DEFAULT_CREATED_BY);
        package_version.setCreated_date(DEFAULT_CREATED_DATE);
        package_version.setModified_by(DEFAULT_MODIFIED_BY);
        package_version.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPackage_version() throws Exception {
        int databaseSizeBeforeCreate = package_versionRepository.findAll().size();

        // Create the Package_version

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isCreated());

        // Validate the Package_version in the database
        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeCreate + 1);
        Package_version testPackage_version = package_versions.get(package_versions.size() - 1);
        assertThat(testPackage_version.getPackage_version_name()).isEqualTo(DEFAULT_PACKAGE_VERSION_NAME);
        assertThat(testPackage_version.getPackage_id()).isEqualTo(DEFAULT_PACKAGE_ID);
        assertThat(testPackage_version.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPackage_version.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testPackage_version.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPackage_version.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackage_version.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPackage_version.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkPackage_version_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setPackage_version_name(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPackage_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setPackage_id(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setDescription(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setStatus_id(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setCreated_by(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setCreated_date(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setModified_by(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = package_versionRepository.findAll().size();
        // set the field null
        package_version.setModified_date(null);

        // Create the Package_version, which fails.

        restPackage_versionMockMvc.perform(post("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isBadRequest());

        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackage_versions() throws Exception {
        // Initialize the database
        package_versionRepository.saveAndFlush(package_version);

        // Get all the package_versions
        restPackage_versionMockMvc.perform(get("/api/package_versions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(package_version.getId().intValue())))
                .andExpect(jsonPath("$.[*].package_version_name").value(hasItem(DEFAULT_PACKAGE_VERSION_NAME.toString())))
                .andExpect(jsonPath("$.[*].package_id").value(hasItem(DEFAULT_PACKAGE_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPackage_version() throws Exception {
        // Initialize the database
        package_versionRepository.saveAndFlush(package_version);

        // Get the package_version
        restPackage_versionMockMvc.perform(get("/api/package_versions/{id}", package_version.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(package_version.getId().intValue()))
            .andExpect(jsonPath("$.package_version_name").value(DEFAULT_PACKAGE_VERSION_NAME.toString()))
            .andExpect(jsonPath("$.package_id").value(DEFAULT_PACKAGE_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackage_version() throws Exception {
        // Get the package_version
        restPackage_versionMockMvc.perform(get("/api/package_versions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackage_version() throws Exception {
        // Initialize the database
        package_versionRepository.saveAndFlush(package_version);

		int databaseSizeBeforeUpdate = package_versionRepository.findAll().size();

        // Update the package_version
        package_version.setPackage_version_name(UPDATED_PACKAGE_VERSION_NAME);
        package_version.setPackage_id(UPDATED_PACKAGE_ID);
        package_version.setDescription(UPDATED_DESCRIPTION);
        package_version.setStatus_id(UPDATED_STATUS_ID);
        package_version.setCreated_by(UPDATED_CREATED_BY);
        package_version.setCreated_date(UPDATED_CREATED_DATE);
        package_version.setModified_by(UPDATED_MODIFIED_BY);
        package_version.setModified_date(UPDATED_MODIFIED_DATE);

        restPackage_versionMockMvc.perform(put("/api/package_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_version)))
                .andExpect(status().isOk());

        // Validate the Package_version in the database
        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeUpdate);
        Package_version testPackage_version = package_versions.get(package_versions.size() - 1);
        assertThat(testPackage_version.getPackage_version_name()).isEqualTo(UPDATED_PACKAGE_VERSION_NAME);
        assertThat(testPackage_version.getPackage_id()).isEqualTo(UPDATED_PACKAGE_ID);
        assertThat(testPackage_version.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPackage_version.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testPackage_version.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackage_version.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackage_version.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPackage_version.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePackage_version() throws Exception {
        // Initialize the database
        package_versionRepository.saveAndFlush(package_version);

		int databaseSizeBeforeDelete = package_versionRepository.findAll().size();

        // Get the package_version
        restPackage_versionMockMvc.perform(delete("/api/package_versions/{id}", package_version.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Package_version> package_versions = package_versionRepository.findAll();
        assertThat(package_versions).hasSize(databaseSizeBeforeDelete - 1);
    }
}

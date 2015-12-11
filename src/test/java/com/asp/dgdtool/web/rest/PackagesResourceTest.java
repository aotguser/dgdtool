package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Packages;
import com.asp.dgdtool.repository.PackagesRepository;
import com.asp.dgdtool.repository.search.PackagesSearchRepository;

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
 * Test class for the PackagesResource REST controller.
 *
 * @see PackagesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PackagesResourceTest {

    private static final String DEFAULT_PACKAGE_NAME = "AAAAA";
    private static final String UPDATED_PACKAGE_NAME = "BBBBB";

    private static final Integer DEFAULT_PACKAGE_TYPE_ID = 1;
    private static final Integer UPDATED_PACKAGE_TYPE_ID = 2;
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
    private PackagesRepository packagesRepository;

    @Inject
    private PackagesSearchRepository packagesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPackagesMockMvc;

    private Packages packages;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PackagesResource packagesResource = new PackagesResource();
        ReflectionTestUtils.setField(packagesResource, "packagesRepository", packagesRepository);
        ReflectionTestUtils.setField(packagesResource, "packagesSearchRepository", packagesSearchRepository);
        this.restPackagesMockMvc = MockMvcBuilders.standaloneSetup(packagesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        packages = new Packages();
        packages.setPackage_name(DEFAULT_PACKAGE_NAME);
        packages.setPackage_type_id(DEFAULT_PACKAGE_TYPE_ID);
        packages.setDescription(DEFAULT_DESCRIPTION);
        packages.setStatus_id(DEFAULT_STATUS_ID);
        packages.setCreated_by(DEFAULT_CREATED_BY);
        packages.setCreated_date(DEFAULT_CREATED_DATE);
        packages.setModified_by(DEFAULT_MODIFIED_BY);
        packages.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPackages() throws Exception {
        int databaseSizeBeforeCreate = packagesRepository.findAll().size();

        // Create the Packages

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isCreated());

        // Validate the Packages in the database
        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeCreate + 1);
        Packages testPackages = packagess.get(packagess.size() - 1);
        assertThat(testPackages.getPackage_name()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testPackages.getPackage_type_id()).isEqualTo(DEFAULT_PACKAGE_TYPE_ID);
        assertThat(testPackages.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPackages.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testPackages.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPackages.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackages.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPackages.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkPackage_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setPackage_name(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPackage_type_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setPackage_type_id(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setDescription(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setStatus_id(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setCreated_by(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setCreated_date(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setModified_by(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = packagesRepository.findAll().size();
        // set the field null
        packages.setModified_date(null);

        // Create the Packages, which fails.

        restPackagesMockMvc.perform(post("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isBadRequest());

        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackagess() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

        // Get all the packagess
        restPackagesMockMvc.perform(get("/api/packagess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(packages.getId().intValue())))
                .andExpect(jsonPath("$.[*].package_name").value(hasItem(DEFAULT_PACKAGE_NAME.toString())))
                .andExpect(jsonPath("$.[*].package_type_id").value(hasItem(DEFAULT_PACKAGE_TYPE_ID)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPackages() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

        // Get the packages
        restPackagesMockMvc.perform(get("/api/packagess/{id}", packages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(packages.getId().intValue()))
            .andExpect(jsonPath("$.package_name").value(DEFAULT_PACKAGE_NAME.toString()))
            .andExpect(jsonPath("$.package_type_id").value(DEFAULT_PACKAGE_TYPE_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackages() throws Exception {
        // Get the packages
        restPackagesMockMvc.perform(get("/api/packagess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackages() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

		int databaseSizeBeforeUpdate = packagesRepository.findAll().size();

        // Update the packages
        packages.setPackage_name(UPDATED_PACKAGE_NAME);
        packages.setPackage_type_id(UPDATED_PACKAGE_TYPE_ID);
        packages.setDescription(UPDATED_DESCRIPTION);
        packages.setStatus_id(UPDATED_STATUS_ID);
        packages.setCreated_by(UPDATED_CREATED_BY);
        packages.setCreated_date(UPDATED_CREATED_DATE);
        packages.setModified_by(UPDATED_MODIFIED_BY);
        packages.setModified_date(UPDATED_MODIFIED_DATE);

        restPackagesMockMvc.perform(put("/api/packagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(packages)))
                .andExpect(status().isOk());

        // Validate the Packages in the database
        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeUpdate);
        Packages testPackages = packagess.get(packagess.size() - 1);
        assertThat(testPackages.getPackage_name()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testPackages.getPackage_type_id()).isEqualTo(UPDATED_PACKAGE_TYPE_ID);
        assertThat(testPackages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPackages.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testPackages.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackages.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackages.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPackages.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePackages() throws Exception {
        // Initialize the database
        packagesRepository.saveAndFlush(packages);

		int databaseSizeBeforeDelete = packagesRepository.findAll().size();

        // Get the packages
        restPackagesMockMvc.perform(delete("/api/packagess/{id}", packages.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Packages> packagess = packagesRepository.findAll();
        assertThat(packagess).hasSize(databaseSizeBeforeDelete - 1);
    }
}

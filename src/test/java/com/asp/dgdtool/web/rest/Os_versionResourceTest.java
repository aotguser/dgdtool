package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Os_version;
import com.asp.dgdtool.repository.Os_versionRepository;
import com.asp.dgdtool.repository.search.Os_versionSearchRepository;

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
 * Test class for the Os_versionResource REST controller.
 *
 * @see Os_versionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Os_versionResourceTest {

    private static final String DEFAULT_OS_VERSION_NAME = "AAAAA";
    private static final String UPDATED_OS_VERSION_NAME = "BBBBB";
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
    private Os_versionRepository os_versionRepository;

    @Inject
    private Os_versionSearchRepository os_versionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOs_versionMockMvc;

    private Os_version os_version;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Os_versionResource os_versionResource = new Os_versionResource();
        ReflectionTestUtils.setField(os_versionResource, "os_versionRepository", os_versionRepository);
        ReflectionTestUtils.setField(os_versionResource, "os_versionSearchRepository", os_versionSearchRepository);
        this.restOs_versionMockMvc = MockMvcBuilders.standaloneSetup(os_versionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        os_version = new Os_version();
        os_version.setOs_version_name(DEFAULT_OS_VERSION_NAME);
        os_version.setDescription(DEFAULT_DESCRIPTION);
        os_version.setStatus_id(DEFAULT_STATUS_ID);
        os_version.setCreated_by(DEFAULT_CREATED_BY);
        os_version.setCreated_date(DEFAULT_CREATED_DATE);
        os_version.setModified_by(DEFAULT_MODIFIED_BY);
        os_version.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createOs_version() throws Exception {
        int databaseSizeBeforeCreate = os_versionRepository.findAll().size();

        // Create the Os_version

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isCreated());

        // Validate the Os_version in the database
        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeCreate + 1);
        Os_version testOs_version = os_versions.get(os_versions.size() - 1);
        assertThat(testOs_version.getOs_version_name()).isEqualTo(DEFAULT_OS_VERSION_NAME);
        assertThat(testOs_version.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOs_version.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testOs_version.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOs_version.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOs_version.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testOs_version.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkOs_version_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_versionRepository.findAll().size();
        // set the field null
        os_version.setOs_version_name(null);

        // Create the Os_version, which fails.

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isBadRequest());

        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_versionRepository.findAll().size();
        // set the field null
        os_version.setDescription(null);

        // Create the Os_version, which fails.

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isBadRequest());

        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_versionRepository.findAll().size();
        // set the field null
        os_version.setStatus_id(null);

        // Create the Os_version, which fails.

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isBadRequest());

        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_versionRepository.findAll().size();
        // set the field null
        os_version.setCreated_by(null);

        // Create the Os_version, which fails.

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isBadRequest());

        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_versionRepository.findAll().size();
        // set the field null
        os_version.setCreated_date(null);

        // Create the Os_version, which fails.

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isBadRequest());

        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_versionRepository.findAll().size();
        // set the field null
        os_version.setModified_by(null);

        // Create the Os_version, which fails.

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isBadRequest());

        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = os_versionRepository.findAll().size();
        // set the field null
        os_version.setModified_date(null);

        // Create the Os_version, which fails.

        restOs_versionMockMvc.perform(post("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isBadRequest());

        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOs_versions() throws Exception {
        // Initialize the database
        os_versionRepository.saveAndFlush(os_version);

        // Get all the os_versions
        restOs_versionMockMvc.perform(get("/api/os_versions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(os_version.getId().intValue())))
                .andExpect(jsonPath("$.[*].os_version_name").value(hasItem(DEFAULT_OS_VERSION_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getOs_version() throws Exception {
        // Initialize the database
        os_versionRepository.saveAndFlush(os_version);

        // Get the os_version
        restOs_versionMockMvc.perform(get("/api/os_versions/{id}", os_version.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(os_version.getId().intValue()))
            .andExpect(jsonPath("$.os_version_name").value(DEFAULT_OS_VERSION_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOs_version() throws Exception {
        // Get the os_version
        restOs_versionMockMvc.perform(get("/api/os_versions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOs_version() throws Exception {
        // Initialize the database
        os_versionRepository.saveAndFlush(os_version);

		int databaseSizeBeforeUpdate = os_versionRepository.findAll().size();

        // Update the os_version
        os_version.setOs_version_name(UPDATED_OS_VERSION_NAME);
        os_version.setDescription(UPDATED_DESCRIPTION);
        os_version.setStatus_id(UPDATED_STATUS_ID);
        os_version.setCreated_by(UPDATED_CREATED_BY);
        os_version.setCreated_date(UPDATED_CREATED_DATE);
        os_version.setModified_by(UPDATED_MODIFIED_BY);
        os_version.setModified_date(UPDATED_MODIFIED_DATE);

        restOs_versionMockMvc.perform(put("/api/os_versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(os_version)))
                .andExpect(status().isOk());

        // Validate the Os_version in the database
        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeUpdate);
        Os_version testOs_version = os_versions.get(os_versions.size() - 1);
        assertThat(testOs_version.getOs_version_name()).isEqualTo(UPDATED_OS_VERSION_NAME);
        assertThat(testOs_version.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOs_version.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testOs_version.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOs_version.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOs_version.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testOs_version.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteOs_version() throws Exception {
        // Initialize the database
        os_versionRepository.saveAndFlush(os_version);

		int databaseSizeBeforeDelete = os_versionRepository.findAll().size();

        // Get the os_version
        restOs_versionMockMvc.perform(delete("/api/os_versions/{id}", os_version.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Os_version> os_versions = os_versionRepository.findAll();
        assertThat(os_versions).hasSize(databaseSizeBeforeDelete - 1);
    }
}

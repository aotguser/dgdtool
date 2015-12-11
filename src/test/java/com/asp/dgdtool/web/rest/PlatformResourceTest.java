package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Platform;
import com.asp.dgdtool.repository.PlatformRepository;
import com.asp.dgdtool.repository.search.PlatformSearchRepository;

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
 * Test class for the PlatformResource REST controller.
 *
 * @see PlatformResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlatformResourceTest {

    private static final String DEFAULT_PLATFORM_NAME = "AAAAA";
    private static final String UPDATED_PLATFORM_NAME = "BBBBB";
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
    private PlatformRepository platformRepository;

    @Inject
    private PlatformSearchRepository platformSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPlatformMockMvc;

    private Platform platform;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlatformResource platformResource = new PlatformResource();
        ReflectionTestUtils.setField(platformResource, "platformRepository", platformRepository);
        ReflectionTestUtils.setField(platformResource, "platformSearchRepository", platformSearchRepository);
        this.restPlatformMockMvc = MockMvcBuilders.standaloneSetup(platformResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        platform = new Platform();
        platform.setPlatform_name(DEFAULT_PLATFORM_NAME);
        platform.setDescription(DEFAULT_DESCRIPTION);
        platform.setStatus_id(DEFAULT_STATUS_ID);
        platform.setCreated_by(DEFAULT_CREATED_BY);
        platform.setCreated_date(DEFAULT_CREATED_DATE);
        platform.setModified_by(DEFAULT_MODIFIED_BY);
        platform.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPlatform() throws Exception {
        int databaseSizeBeforeCreate = platformRepository.findAll().size();

        // Create the Platform

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isCreated());

        // Validate the Platform in the database
        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeCreate + 1);
        Platform testPlatform = platforms.get(platforms.size() - 1);
        assertThat(testPlatform.getPlatform_name()).isEqualTo(DEFAULT_PLATFORM_NAME);
        assertThat(testPlatform.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlatform.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testPlatform.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlatform.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPlatform.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPlatform.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkPlatform_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setPlatform_name(null);

        // Create the Platform, which fails.

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isBadRequest());

        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setDescription(null);

        // Create the Platform, which fails.

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isBadRequest());

        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setStatus_id(null);

        // Create the Platform, which fails.

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isBadRequest());

        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setCreated_by(null);

        // Create the Platform, which fails.

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isBadRequest());

        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setCreated_date(null);

        // Create the Platform, which fails.

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isBadRequest());

        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setModified_by(null);

        // Create the Platform, which fails.

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isBadRequest());

        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformRepository.findAll().size();
        // set the field null
        platform.setModified_date(null);

        // Create the Platform, which fails.

        restPlatformMockMvc.perform(post("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isBadRequest());

        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlatforms() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        // Get all the platforms
        restPlatformMockMvc.perform(get("/api/platforms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(platform.getId().intValue())))
                .andExpect(jsonPath("$.[*].platform_name").value(hasItem(DEFAULT_PLATFORM_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPlatform() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

        // Get the platform
        restPlatformMockMvc.perform(get("/api/platforms/{id}", platform.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(platform.getId().intValue()))
            .andExpect(jsonPath("$.platform_name").value(DEFAULT_PLATFORM_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlatform() throws Exception {
        // Get the platform
        restPlatformMockMvc.perform(get("/api/platforms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlatform() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

		int databaseSizeBeforeUpdate = platformRepository.findAll().size();

        // Update the platform
        platform.setPlatform_name(UPDATED_PLATFORM_NAME);
        platform.setDescription(UPDATED_DESCRIPTION);
        platform.setStatus_id(UPDATED_STATUS_ID);
        platform.setCreated_by(UPDATED_CREATED_BY);
        platform.setCreated_date(UPDATED_CREATED_DATE);
        platform.setModified_by(UPDATED_MODIFIED_BY);
        platform.setModified_date(UPDATED_MODIFIED_DATE);

        restPlatformMockMvc.perform(put("/api/platforms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(platform)))
                .andExpect(status().isOk());

        // Validate the Platform in the database
        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeUpdate);
        Platform testPlatform = platforms.get(platforms.size() - 1);
        assertThat(testPlatform.getPlatform_name()).isEqualTo(UPDATED_PLATFORM_NAME);
        assertThat(testPlatform.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlatform.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testPlatform.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlatform.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlatform.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPlatform.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deletePlatform() throws Exception {
        // Initialize the database
        platformRepository.saveAndFlush(platform);

		int databaseSizeBeforeDelete = platformRepository.findAll().size();

        // Get the platform
        restPlatformMockMvc.perform(delete("/api/platforms/{id}", platform.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Platform> platforms = platformRepository.findAll();
        assertThat(platforms).hasSize(databaseSizeBeforeDelete - 1);
    }
}

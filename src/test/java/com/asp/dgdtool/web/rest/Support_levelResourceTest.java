package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Support_level;
import com.asp.dgdtool.repository.Support_levelRepository;
import com.asp.dgdtool.repository.search.Support_levelSearchRepository;

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
 * Test class for the Support_levelResource REST controller.
 *
 * @see Support_levelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Support_levelResourceTest {

    private static final String DEFAULT_SUPPORT_LEVEL_NAME = "AAAAA";
    private static final String UPDATED_SUPPORT_LEVEL_NAME = "BBBBB";
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
    private Support_levelRepository support_levelRepository;

    @Inject
    private Support_levelSearchRepository support_levelSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSupport_levelMockMvc;

    private Support_level support_level;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Support_levelResource support_levelResource = new Support_levelResource();
        ReflectionTestUtils.setField(support_levelResource, "support_levelRepository", support_levelRepository);
        ReflectionTestUtils.setField(support_levelResource, "support_levelSearchRepository", support_levelSearchRepository);
        this.restSupport_levelMockMvc = MockMvcBuilders.standaloneSetup(support_levelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        support_level = new Support_level();
        support_level.setSupport_level_name(DEFAULT_SUPPORT_LEVEL_NAME);
        support_level.setDescription(DEFAULT_DESCRIPTION);
        support_level.setStatus_id(DEFAULT_STATUS_ID);
        support_level.setCreated_by(DEFAULT_CREATED_BY);
        support_level.setCreated_date(DEFAULT_CREATED_DATE);
        support_level.setModified_by(DEFAULT_MODIFIED_BY);
        support_level.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSupport_level() throws Exception {
        int databaseSizeBeforeCreate = support_levelRepository.findAll().size();

        // Create the Support_level

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isCreated());

        // Validate the Support_level in the database
        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeCreate + 1);
        Support_level testSupport_level = support_levels.get(support_levels.size() - 1);
        assertThat(testSupport_level.getSupport_level_name()).isEqualTo(DEFAULT_SUPPORT_LEVEL_NAME);
        assertThat(testSupport_level.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSupport_level.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testSupport_level.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupport_level.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSupport_level.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSupport_level.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkSupport_level_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = support_levelRepository.findAll().size();
        // set the field null
        support_level.setSupport_level_name(null);

        // Create the Support_level, which fails.

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isBadRequest());

        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = support_levelRepository.findAll().size();
        // set the field null
        support_level.setDescription(null);

        // Create the Support_level, which fails.

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isBadRequest());

        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = support_levelRepository.findAll().size();
        // set the field null
        support_level.setStatus_id(null);

        // Create the Support_level, which fails.

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isBadRequest());

        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = support_levelRepository.findAll().size();
        // set the field null
        support_level.setCreated_by(null);

        // Create the Support_level, which fails.

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isBadRequest());

        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = support_levelRepository.findAll().size();
        // set the field null
        support_level.setCreated_date(null);

        // Create the Support_level, which fails.

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isBadRequest());

        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = support_levelRepository.findAll().size();
        // set the field null
        support_level.setModified_by(null);

        // Create the Support_level, which fails.

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isBadRequest());

        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = support_levelRepository.findAll().size();
        // set the field null
        support_level.setModified_date(null);

        // Create the Support_level, which fails.

        restSupport_levelMockMvc.perform(post("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isBadRequest());

        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupport_levels() throws Exception {
        // Initialize the database
        support_levelRepository.saveAndFlush(support_level);

        // Get all the support_levels
        restSupport_levelMockMvc.perform(get("/api/support_levels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(support_level.getId().intValue())))
                .andExpect(jsonPath("$.[*].support_level_name").value(hasItem(DEFAULT_SUPPORT_LEVEL_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSupport_level() throws Exception {
        // Initialize the database
        support_levelRepository.saveAndFlush(support_level);

        // Get the support_level
        restSupport_levelMockMvc.perform(get("/api/support_levels/{id}", support_level.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(support_level.getId().intValue()))
            .andExpect(jsonPath("$.support_level_name").value(DEFAULT_SUPPORT_LEVEL_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupport_level() throws Exception {
        // Get the support_level
        restSupport_levelMockMvc.perform(get("/api/support_levels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupport_level() throws Exception {
        // Initialize the database
        support_levelRepository.saveAndFlush(support_level);

		int databaseSizeBeforeUpdate = support_levelRepository.findAll().size();

        // Update the support_level
        support_level.setSupport_level_name(UPDATED_SUPPORT_LEVEL_NAME);
        support_level.setDescription(UPDATED_DESCRIPTION);
        support_level.setStatus_id(UPDATED_STATUS_ID);
        support_level.setCreated_by(UPDATED_CREATED_BY);
        support_level.setCreated_date(UPDATED_CREATED_DATE);
        support_level.setModified_by(UPDATED_MODIFIED_BY);
        support_level.setModified_date(UPDATED_MODIFIED_DATE);

        restSupport_levelMockMvc.perform(put("/api/support_levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(support_level)))
                .andExpect(status().isOk());

        // Validate the Support_level in the database
        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeUpdate);
        Support_level testSupport_level = support_levels.get(support_levels.size() - 1);
        assertThat(testSupport_level.getSupport_level_name()).isEqualTo(UPDATED_SUPPORT_LEVEL_NAME);
        assertThat(testSupport_level.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSupport_level.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testSupport_level.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupport_level.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSupport_level.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSupport_level.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteSupport_level() throws Exception {
        // Initialize the database
        support_levelRepository.saveAndFlush(support_level);

		int databaseSizeBeforeDelete = support_levelRepository.findAll().size();

        // Get the support_level
        restSupport_levelMockMvc.perform(delete("/api/support_levels/{id}", support_level.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Support_level> support_levels = support_levelRepository.findAll();
        assertThat(support_levels).hasSize(databaseSizeBeforeDelete - 1);
    }
}

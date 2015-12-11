package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Initiative_type;
import com.asp.dgdtool.repository.Initiative_typeRepository;
import com.asp.dgdtool.repository.search.Initiative_typeSearchRepository;

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
 * Test class for the Initiative_typeResource REST controller.
 *
 * @see Initiative_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Initiative_typeResourceTest {

    private static final String DEFAULT_INITIATIVE_TYPE_NAME = "AAAAA";
    private static final String UPDATED_INITIATIVE_TYPE_NAME = "BBBBB";
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
    private Initiative_typeRepository initiative_typeRepository;

    @Inject
    private Initiative_typeSearchRepository initiative_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInitiative_typeMockMvc;

    private Initiative_type initiative_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Initiative_typeResource initiative_typeResource = new Initiative_typeResource();
        ReflectionTestUtils.setField(initiative_typeResource, "initiative_typeRepository", initiative_typeRepository);
        ReflectionTestUtils.setField(initiative_typeResource, "initiative_typeSearchRepository", initiative_typeSearchRepository);
        this.restInitiative_typeMockMvc = MockMvcBuilders.standaloneSetup(initiative_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        initiative_type = new Initiative_type();
        initiative_type.setInitiative_type_name(DEFAULT_INITIATIVE_TYPE_NAME);
        initiative_type.setDescription(DEFAULT_DESCRIPTION);
        initiative_type.setStatus_id(DEFAULT_STATUS_ID);
        initiative_type.setCreated_by(DEFAULT_CREATED_BY);
        initiative_type.setCreated_date(DEFAULT_CREATED_DATE);
        initiative_type.setModified_by(DEFAULT_MODIFIED_BY);
        initiative_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createInitiative_type() throws Exception {
        int databaseSizeBeforeCreate = initiative_typeRepository.findAll().size();

        // Create the Initiative_type

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isCreated());

        // Validate the Initiative_type in the database
        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeCreate + 1);
        Initiative_type testInitiative_type = initiative_types.get(initiative_types.size() - 1);
        assertThat(testInitiative_type.getInitiative_type_name()).isEqualTo(DEFAULT_INITIATIVE_TYPE_NAME);
        assertThat(testInitiative_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInitiative_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testInitiative_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInitiative_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInitiative_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testInitiative_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkInitiative_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiative_typeRepository.findAll().size();
        // set the field null
        initiative_type.setInitiative_type_name(null);

        // Create the Initiative_type, which fails.

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isBadRequest());

        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiative_typeRepository.findAll().size();
        // set the field null
        initiative_type.setDescription(null);

        // Create the Initiative_type, which fails.

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isBadRequest());

        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiative_typeRepository.findAll().size();
        // set the field null
        initiative_type.setStatus_id(null);

        // Create the Initiative_type, which fails.

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isBadRequest());

        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiative_typeRepository.findAll().size();
        // set the field null
        initiative_type.setCreated_by(null);

        // Create the Initiative_type, which fails.

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isBadRequest());

        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiative_typeRepository.findAll().size();
        // set the field null
        initiative_type.setCreated_date(null);

        // Create the Initiative_type, which fails.

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isBadRequest());

        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiative_typeRepository.findAll().size();
        // set the field null
        initiative_type.setModified_by(null);

        // Create the Initiative_type, which fails.

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isBadRequest());

        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = initiative_typeRepository.findAll().size();
        // set the field null
        initiative_type.setModified_date(null);

        // Create the Initiative_type, which fails.

        restInitiative_typeMockMvc.perform(post("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isBadRequest());

        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInitiative_types() throws Exception {
        // Initialize the database
        initiative_typeRepository.saveAndFlush(initiative_type);

        // Get all the initiative_types
        restInitiative_typeMockMvc.perform(get("/api/initiative_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(initiative_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].initiative_type_name").value(hasItem(DEFAULT_INITIATIVE_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInitiative_type() throws Exception {
        // Initialize the database
        initiative_typeRepository.saveAndFlush(initiative_type);

        // Get the initiative_type
        restInitiative_typeMockMvc.perform(get("/api/initiative_types/{id}", initiative_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(initiative_type.getId().intValue()))
            .andExpect(jsonPath("$.initiative_type_name").value(DEFAULT_INITIATIVE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInitiative_type() throws Exception {
        // Get the initiative_type
        restInitiative_typeMockMvc.perform(get("/api/initiative_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInitiative_type() throws Exception {
        // Initialize the database
        initiative_typeRepository.saveAndFlush(initiative_type);

		int databaseSizeBeforeUpdate = initiative_typeRepository.findAll().size();

        // Update the initiative_type
        initiative_type.setInitiative_type_name(UPDATED_INITIATIVE_TYPE_NAME);
        initiative_type.setDescription(UPDATED_DESCRIPTION);
        initiative_type.setStatus_id(UPDATED_STATUS_ID);
        initiative_type.setCreated_by(UPDATED_CREATED_BY);
        initiative_type.setCreated_date(UPDATED_CREATED_DATE);
        initiative_type.setModified_by(UPDATED_MODIFIED_BY);
        initiative_type.setModified_date(UPDATED_MODIFIED_DATE);

        restInitiative_typeMockMvc.perform(put("/api/initiative_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(initiative_type)))
                .andExpect(status().isOk());

        // Validate the Initiative_type in the database
        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeUpdate);
        Initiative_type testInitiative_type = initiative_types.get(initiative_types.size() - 1);
        assertThat(testInitiative_type.getInitiative_type_name()).isEqualTo(UPDATED_INITIATIVE_TYPE_NAME);
        assertThat(testInitiative_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInitiative_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testInitiative_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInitiative_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInitiative_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testInitiative_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteInitiative_type() throws Exception {
        // Initialize the database
        initiative_typeRepository.saveAndFlush(initiative_type);

		int databaseSizeBeforeDelete = initiative_typeRepository.findAll().size();

        // Get the initiative_type
        restInitiative_typeMockMvc.perform(delete("/api/initiative_types/{id}", initiative_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Initiative_type> initiative_types = initiative_typeRepository.findAll();
        assertThat(initiative_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

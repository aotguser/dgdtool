package com.asp.dgdtool.web.rest;

import com.asp.dgdtool.Application;
import com.asp.dgdtool.domain.Asset_type;
import com.asp.dgdtool.repository.Asset_typeRepository;
import com.asp.dgdtool.repository.search.Asset_typeSearchRepository;

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
 * Test class for the Asset_typeResource REST controller.
 *
 * @see Asset_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Asset_typeResourceTest {

    private static final String DEFAULT_ASSET_TYPE_NAME = "AAAAA";
    private static final String UPDATED_ASSET_TYPE_NAME = "BBBBB";
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
    private Asset_typeRepository asset_typeRepository;

    @Inject
    private Asset_typeSearchRepository asset_typeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAsset_typeMockMvc;

    private Asset_type asset_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Asset_typeResource asset_typeResource = new Asset_typeResource();
        ReflectionTestUtils.setField(asset_typeResource, "asset_typeRepository", asset_typeRepository);
        ReflectionTestUtils.setField(asset_typeResource, "asset_typeSearchRepository", asset_typeSearchRepository);
        this.restAsset_typeMockMvc = MockMvcBuilders.standaloneSetup(asset_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        asset_type = new Asset_type();
        asset_type.setAsset_type_name(DEFAULT_ASSET_TYPE_NAME);
        asset_type.setDescription(DEFAULT_DESCRIPTION);
        asset_type.setStatus_id(DEFAULT_STATUS_ID);
        asset_type.setCreated_by(DEFAULT_CREATED_BY);
        asset_type.setCreated_date(DEFAULT_CREATED_DATE);
        asset_type.setModified_by(DEFAULT_MODIFIED_BY);
        asset_type.setModified_date(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createAsset_type() throws Exception {
        int databaseSizeBeforeCreate = asset_typeRepository.findAll().size();

        // Create the Asset_type

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isCreated());

        // Validate the Asset_type in the database
        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeCreate + 1);
        Asset_type testAsset_type = asset_types.get(asset_types.size() - 1);
        assertThat(testAsset_type.getAsset_type_name()).isEqualTo(DEFAULT_ASSET_TYPE_NAME);
        assertThat(testAsset_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAsset_type.getStatus_id()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testAsset_type.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAsset_type.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAsset_type.getModified_by()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAsset_type.getModified_date()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void checkAsset_type_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = asset_typeRepository.findAll().size();
        // set the field null
        asset_type.setAsset_type_name(null);

        // Create the Asset_type, which fails.

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isBadRequest());

        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = asset_typeRepository.findAll().size();
        // set the field null
        asset_type.setDescription(null);

        // Create the Asset_type, which fails.

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isBadRequest());

        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatus_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = asset_typeRepository.findAll().size();
        // set the field null
        asset_type.setStatus_id(null);

        // Create the Asset_type, which fails.

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isBadRequest());

        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = asset_typeRepository.findAll().size();
        // set the field null
        asset_type.setCreated_by(null);

        // Create the Asset_type, which fails.

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isBadRequest());

        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = asset_typeRepository.findAll().size();
        // set the field null
        asset_type.setCreated_date(null);

        // Create the Asset_type, which fails.

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isBadRequest());

        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_byIsRequired() throws Exception {
        int databaseSizeBeforeTest = asset_typeRepository.findAll().size();
        // set the field null
        asset_type.setModified_by(null);

        // Create the Asset_type, which fails.

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isBadRequest());

        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModified_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = asset_typeRepository.findAll().size();
        // set the field null
        asset_type.setModified_date(null);

        // Create the Asset_type, which fails.

        restAsset_typeMockMvc.perform(post("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isBadRequest());

        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAsset_types() throws Exception {
        // Initialize the database
        asset_typeRepository.saveAndFlush(asset_type);

        // Get all the asset_types
        restAsset_typeMockMvc.perform(get("/api/asset_types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(asset_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].asset_type_name").value(hasItem(DEFAULT_ASSET_TYPE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status_id").value(hasItem(DEFAULT_STATUS_ID)))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE.toString())))
                .andExpect(jsonPath("$.[*].modified_by").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
                .andExpect(jsonPath("$.[*].modified_date").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAsset_type() throws Exception {
        // Initialize the database
        asset_typeRepository.saveAndFlush(asset_type);

        // Get the asset_type
        restAsset_typeMockMvc.perform(get("/api/asset_types/{id}", asset_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(asset_type.getId().intValue()))
            .andExpect(jsonPath("$.asset_type_name").value(DEFAULT_ASSET_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status_id").value(DEFAULT_STATUS_ID))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modified_by").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modified_date").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAsset_type() throws Exception {
        // Get the asset_type
        restAsset_typeMockMvc.perform(get("/api/asset_types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsset_type() throws Exception {
        // Initialize the database
        asset_typeRepository.saveAndFlush(asset_type);

		int databaseSizeBeforeUpdate = asset_typeRepository.findAll().size();

        // Update the asset_type
        asset_type.setAsset_type_name(UPDATED_ASSET_TYPE_NAME);
        asset_type.setDescription(UPDATED_DESCRIPTION);
        asset_type.setStatus_id(UPDATED_STATUS_ID);
        asset_type.setCreated_by(UPDATED_CREATED_BY);
        asset_type.setCreated_date(UPDATED_CREATED_DATE);
        asset_type.setModified_by(UPDATED_MODIFIED_BY);
        asset_type.setModified_date(UPDATED_MODIFIED_DATE);

        restAsset_typeMockMvc.perform(put("/api/asset_types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(asset_type)))
                .andExpect(status().isOk());

        // Validate the Asset_type in the database
        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeUpdate);
        Asset_type testAsset_type = asset_types.get(asset_types.size() - 1);
        assertThat(testAsset_type.getAsset_type_name()).isEqualTo(UPDATED_ASSET_TYPE_NAME);
        assertThat(testAsset_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAsset_type.getStatus_id()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testAsset_type.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAsset_type.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsset_type.getModified_by()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAsset_type.getModified_date()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void deleteAsset_type() throws Exception {
        // Initialize the database
        asset_typeRepository.saveAndFlush(asset_type);

		int databaseSizeBeforeDelete = asset_typeRepository.findAll().size();

        // Get the asset_type
        restAsset_typeMockMvc.perform(delete("/api/asset_types/{id}", asset_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Asset_type> asset_types = asset_typeRepository.findAll();
        assertThat(asset_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
